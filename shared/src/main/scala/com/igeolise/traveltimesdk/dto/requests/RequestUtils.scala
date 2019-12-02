package com.igeolise.traveltimesdk.dto.requests

import cats.Monad
import cats.implicits._
import com.igeolise.traveltimesdk.dto.common.BCP47
import com.igeolise.traveltimesdk.dto.responses.{
  GeoJsonResponse,
  GeocodingLanguageResponse,
  GeocodingResponse,
  GeocodingResponseProperties,
  TravelTimeSdkError
}
import com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError.ValidationError
import com.igeolise.traveltimesdk.json.reads.ErrorReads._
import com.softwaremill.{sttp => STTP}
import com.softwaremill.sttp.{Request, _}
import play.api.libs.json._
import scala.concurrent.duration._

object RequestUtils {

  trait TravelTimePlatformRequest[Response <: TravelTimePlatformResponse] {
    def send[R[_]: Monad, S](sttpRequest: SttpRequest[R, S]): R[Either[TravelTimeSdkError, Response]]

    def sttpRequest(host: Uri): Request[String, Nothing]
  }

  trait TravelTimePlatformResponse

  case class SttpRequest[R[_]: Monad, S](
      backend: SttpBackend[R, S],
      request: Request[String, S]
  )

  def sendModified[R[_]: Monad, S, Response](
      sttpRequest: SttpRequest[R, S],
      modifyResponseFn: STTP.Response[String] => Either[TravelTimeSdkError, Response]
  ): R[Either[TravelTimeSdkError, Response]] = {

    sttpRequest.backend
      .send(sttpRequest.request)
      .map(modifyResponseFn)
  }

  def addLanguageToResponse(
      validationFn: JsValue => JsResult[
        GeoJsonResponse[GeocodingResponseProperties]]
  ): STTP.Response[String] => Either[TravelTimeSdkError, GeocodingResponse] =
    response => {

      def findContentLanguageHeader(headers: Seq[(String, String)]): BCP47 = {
        headers.find(_._1 == "Content-Language") match {
          case Some(value) => BCP47(value._2)
          case None        => BCP47("en")
        }
      }

      response.body
        .handleJsonResponse(validationFn)
        .map { properties =>
          GeocodingResponse(
            language = GeocodingLanguageResponse(
              findContentLanguageHeader(response.headers)),
            properties = properties
          )
        }
    }

  def send[R[_]: Monad, S, Response](
      sttpRequest: SttpRequest[R, S],
      validationFn: JsValue => JsResult[Response]
  ): R[Either[TravelTimeSdkError, Response]] = {

    val defaultModifyFn = (r: STTP.Response[String]) =>
      r.body.handleJsonResponse(validationFn)

    sendModified(sttpRequest, defaultModifyFn)
  }

  def makePostRequest(requestBody: JsValue,
                      endPoint: String,
                      host: Uri): Request[String, Nothing] = {
    sttp
      .readTimeout(5.minutes)
      .body(requestBody.toString)
      .contentType(MediaTypes.Json)
      .post(uri"$host/${endPoint.split('/').map(_.trim).toList}")
  }

  implicit class HandleJsonResponse(self: Either[String, String]) {
    def handleJsonResponse[Response](
        validationFn: JsValue => JsResult[Response]
    ): Either[TravelTimeSdkError, Response] =
      self match {
        case Left(err) => Left(handleErrorResponse(err))
        case Right(value) =>
          val jsVal = Json.parse(value)
          validationFn(jsVal) match {
            case e: JsError             => Left(ValidationError(e))
            case s: JsSuccess[Response] => Right(s.get)
          }
      }

    def getJson[Response](
        validationFn: JsValue => JsResult[Response]
    ): Either[TravelTimeSdkError, JsValue] =
      self match {
        case Left(err) => Left(handleErrorResponse(err))
        case Right(value) =>
          validationFn(Json.parse(value)) match {
            case e: JsError             => Left(ValidationError(e))
            case s: JsSuccess[Response] => Right(s.asInstanceOf[JsValue])
          }
      }
  }

  def handleErrorResponse(errString: String): TravelTimeSdkError = {
    Json
      .parse(errString)
      .validate[TravelTimeSdkError.ErrorResponseDetails] match {
      case JsSuccess(value, _) => TravelTimeSdkError.ErrorResponse(value)
      case a: JsError          => ValidationError(a)
    }
  }

}
