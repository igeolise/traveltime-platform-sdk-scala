package com.igeolise.traveltimesdk.json.reads

import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeMapProps.TimeMapResponseProperties
import com.igeolise.traveltimesdk.dto.responses.TimeMapWktResponse
import play.api.libs.functional.syntax._
import com.igeolise.traveltimesdk.json.reads.CommonReads._
import play.api.libs.json.{JsValue, Reads, __}

object TimeMapWktReads {

  implicit val timeMapWktSingleResultReads: Reads[TimeMapWktResponse.SingleSearchResult] = (
    (__ \ "search_id").read[String] and
    (__ \ "shape").read[String] and
    (__ \ "properties").read[TimeMapResponseProperties]
  ) (TimeMapWktResponse.SingleSearchResult.apply _)

  implicit val timeMapWktResponseReads: Reads[TimeMapWktResponse] =(
    (__ \ "results").read[Seq[TimeMapWktResponse.SingleSearchResult]] and
    __.read[JsValue]
  )(TimeMapWktResponse.apply _)
}
