package com.traveltime.sdk.json.reads

import com.traveltime.sdk.json.reads.CommonReads._
import com.traveltime.sdk.dto.common.Coords
import com.traveltime.sdk.dto.requests.common.TimeMapProps.TimeMapResponseProperties
import com.traveltime.sdk.dto.responses.TimeMapResponse
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsValue, Reads, __}

object TimeMapReads {

  implicit val timeMapShapeReads: Reads[TimeMapResponse.Shape] = (
    (__ \ "shell").read[Seq[Coords]](Reads.seq(coordsReads)) and
    (__ \ "holes").read[Seq[Seq[Coords]]](Reads.seq(Reads.seq(coordsReads)))
  ) (TimeMapResponse.Shape.apply _)

  implicit val timeMapSingleSearchResultReads: Reads[TimeMapResponse.SingleSearchResult] = (
    (__ \ "search_id").read[String] and
    (__ \ "shapes").read[Seq[TimeMapResponse.Shape]] and
    (__ \ "properties").read[TimeMapResponseProperties]
  ) (TimeMapResponse.SingleSearchResult.apply _)

  implicit val timeMapResponseReads: Reads[TimeMapResponse] =(
    (__ \ "results").read[Seq[TimeMapResponse.SingleSearchResult]] and
    __.read[JsValue]
  )(TimeMapResponse.apply _)
}