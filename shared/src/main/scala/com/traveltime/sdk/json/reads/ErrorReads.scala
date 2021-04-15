package com.traveltime.sdk.json.reads

import com.traveltime.sdk.dto.responses.TravelTimeSdkError
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}

object ErrorReads {
  implicit val requestErrorReads: Reads[TravelTimeSdkError.ErrorResponseDetails] = (
    (__ \ "http_status").read[Int] and
    (__ \ "error_code").read[Int] and
    (__ \ "description").read[String] and
    (__ \ "documentation_link").read[String] and
    (__ \ "additional_info").read[Map[String, Seq[String]]]
  ) (TravelTimeSdkError.ErrorResponseDetails.apply _)
}
