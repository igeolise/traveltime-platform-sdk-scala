package com.igeolise.traveltimesdk.dto.responses

import play.api.libs.json.JsError

sealed trait TravelTimeSdkError

object TravelTimeSdkError {
  case class ConnectionError(cause: Throwable) extends TravelTimeSdkError
  case class ValidationError(err: JsError) extends TravelTimeSdkError
  case class ErrorResponse(response: ErrorResponseDetails) extends TravelTimeSdkError
  case class JsonParseError(cause: Throwable) extends TravelTimeSdkError
  case class UriValidationError(message: String) extends TravelTimeSdkError

  case class ErrorResponseDetails(
    httpStatus: Int,
    code: Int,
    description: String,
    documentationLink: String,
    additionalInfo: Map[String, Seq[String]]
  )
}
