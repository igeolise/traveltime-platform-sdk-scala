package com.igeolise.traveltimesdk.json.reads

import com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}

object ErrorReads {

  implicit val requestErrorReads: Reads[TravelTimeSdkError.ErroResponseDetails] = (
    (__ \ "http_status").read[Int] and
    (__ \ "error_code").read[Int] and
    (__ \ "description").read[String] and
    (__ \ "documentation_link").read[String] and
    (__ \ "additional_info").read[Map[String, Seq[String]]]
  ) (TravelTimeSdkError.ErroResponseDetails.apply _)

}
