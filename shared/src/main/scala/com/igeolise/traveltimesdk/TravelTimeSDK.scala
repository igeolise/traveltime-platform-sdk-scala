package com.igeolise.traveltimesdk

import com.igeolise.traveltimesdk.TravelTimeSDK.{TravelTimeRequest, TravelTimeResponse}
import com.igeolise.traveltimesdk.dto.common.BCP47
import com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError.{ExceptionError, JsonParseError, ValidationError}
import com.igeolise.traveltimesdk.dto.responses._
import com.igeolise.traveltimesdk.json.reads.ErrorReads._
import play.api.libs.json._
import sttp.client.monad.MonadError
import sttp.client.monad.syntax._
import sttp.client.{Empty, Request, RequestT, Response, SttpBackend, basicRequest}
import sttp.model.{Header, MediaType, Uri}

import scala.concurrent.duration.DurationInt
import scala.language.higherKinds
import scala.util.{Failure, Success, Try}

case class TravelTimeSDK[F[_], S, WS[_]](
  credentials: ApiCredentials,
  host: TravelTimeHost
)(
  implicit val backend: SttpBackend[F, S, WS]
) {

  implicit lazy val monadInstance: MonadError[F] = backend.responseMonad

  /**
   * Sends a provided request. On failure puts an exception to error channel.
   */
  def send[A <: TravelTimeResponse, Result](request: TravelTimeRequest[A]): F[A] =
    send_(request).flatMap {
      case Right(value)                => monadInstance.unit(value)
      case Left(error: ExceptionError) => monadInstance.error(new RuntimeException(error.message, error.cause))
      case Left(error)                 => monadInstance.error(new RuntimeException(error.message))
    }

  /**
   * Sends a provided request and wraps result into an inner Either which has [[com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError]]
   * as it's error channel.
   */
  def send_[A <: TravelTimeResponse, Result](request: TravelTimeRequest[A]): F[Either[TravelTimeSdkError, A]] =
    request
      .sttpRequest(host)
      .headers(credentials.toHeaders:_*)
      .send()
      .map(request.transform)
}

object TravelTimeSDK {

  /**
  * `FailureBody` is used if the status code is non-2xx and `SuccessBody` otherwise.
  */
  sealed trait ResponseBody { val content: String }
  case class SuccessBody(content: String) extends ResponseBody
  case class FailureBody(content: String) extends ResponseBody

  type TransformFn[Result] = Response[ResponseBody] => Either[TravelTimeSdkError, Result]

  trait TravelTimeRequest[Result <: TravelTimeResponse] {
    val transform: TransformFn[Result]
    def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S]
  }

  trait TravelTimeResponse

  def createPostRequest(requestBody: JsValue, uri: Uri): Request[ResponseBody, Nothing] =
    requestBase
      .body(requestBody.toString)
      .post(uri)

  def createGetRequest(uri: Uri): Request[ResponseBody, Nothing] =
    requestBase.get(uri)

  def requestBase: RequestT[Empty, ResponseBody, Nothing] =
    basicRequest
      .readTimeout(5.minutes)
      .contentType(MediaType.ApplicationJson)
      .mapResponse(_.fold(FailureBody, SuccessBody))

  def addLanguageToResponse(
    validationFn: JsValue => JsResult[GeoJsonResponse[GeocodingResponseProperties]]
  ): Response[ResponseBody] => Either[TravelTimeSdkError, GeocodingResponse] =
    response =>
      handleJsonResponse(response.body, validationFn)
        .right
        .map( properties => GeocodingResponse(
          GeocodingLanguageResponse(findContentLanguageHeader(response.headers)), properties
        ))

  def handleJsonResponse[Result](
    body: ResponseBody,
    validateFn: JsValue => JsResult[Result]
  ): Either[TravelTimeSdkError, Result] =
    body match {
      case FailureBody(errorBody)   => Left(handleErrorResponse(errorBody))
      case SuccessBody(successBody) => parse(successBody, validateFn)
    }

  def handleErrorResponse(errString: String): TravelTimeSdkError =
    Try(Json.parse(errString).validate[TravelTimeSdkError.ErrorResponseDetails]) match {
      case Failure(exception)           => JsonParseError(exception)
      case Success(JsSuccess(value, _)) => TravelTimeSdkError.ErrorResponse(value)
      case Success(a : JsError)         => ValidationError(a)
    }

  def parse[Result](content: String, validateFn: JsValue => JsResult[Result]): Either[TravelTimeSdkError, Result] =
    Try(validateFn(Json.parse(content))) match {
      case Failure(exception)           => Left(JsonParseError(exception))
      case Success(JsSuccess(value, _)) => Right(value)
      case Success(e: JsError)          => Left(ValidationError(e))
    }

  def findContentLanguageHeader(headers: Seq[Header]): BCP47 =
    headers.find(_.name == "Content-Language") match {
      case Some(header) => BCP47(header.value)
      case None         => BCP47("en")
    }
}
