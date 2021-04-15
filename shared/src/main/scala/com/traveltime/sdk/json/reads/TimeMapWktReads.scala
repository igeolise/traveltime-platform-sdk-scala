package com.traveltime.sdk.json.reads

import com.traveltime.sdk.dto.requests.common.TimeMapProps.TimeMapResponseProperties
import com.traveltime.sdk.dto.responses.TimeMapWktResponse
import play.api.libs.functional.syntax._
import com.traveltime.sdk.json.reads.CommonReads._
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
