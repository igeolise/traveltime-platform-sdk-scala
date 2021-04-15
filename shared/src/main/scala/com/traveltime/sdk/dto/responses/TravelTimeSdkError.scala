package com.traveltime.sdk.dto.responses

import play.api.libs.json.JsError

sealed abstract class TravelTimeSdkError(val message: String)

object TravelTimeSdkError {
  sealed trait ExceptionError { val cause: Throwable }

  case class UriValidationError(errorMessage: String) extends TravelTimeSdkError(errorMessage)
  case class ValidationError(err: JsError) extends TravelTimeSdkError(s"Validation error $err")
  case class ConnectionError(cause: Throwable) extends TravelTimeSdkError("Connection error") with ExceptionError
  case class JsonParseError(cause: Throwable) extends TravelTimeSdkError("Json failed to parse") with ExceptionError
  case class ErrorResponse(response: ErrorResponseDetails) extends TravelTimeSdkError(s"API returned an error: $response")

  case class ErrorResponseDetails(
    httpStatus: Int,
    code: Int,
    description: String,
    documentationLink: String,
    additionalInfo: Map[String, Seq[String]]
  )
}
