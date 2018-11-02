package com.igeolise.traveltimesdk.json.reads

import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeMapProps.TimeMapResponseProperties
import com.igeolise.traveltimesdk.dto.responses.TimeMapBoxesResponse
import com.igeolise.traveltimesdk.dto.responses.TimeMapBoxesResponse.{BoundingBox, Container}
import play.api.libs.functional.syntax._
import com.igeolise.traveltimesdk.json.reads.CommonReads._
import play.api.libs.json.{JsValue, Reads, __}

object TimeMapBoxesReads {

  implicit val containerReads: Reads[Container] = (
    (__ \ "min_lat").read[Double] and
    (__ \ "max_lat").read[Double] and
    (__ \ "min_lng").read[Double] and
    (__ \ "max_lng").read[Double]
  ) (Container.apply _)

  implicit val boundingBoxesReads: Reads[BoundingBox] = (
    (__ \ "envelope").read[Container] and
    (__ \ "boxes").read[Seq[Container]]
  ) (BoundingBox.apply _)

  implicit val timeMapBoxesSingleResultReads: Reads[TimeMapBoxesResponse.SingleSearchResult] = (
    (__ \ "search_id").read[String] and
    (__ \ "bounding_boxes").read[Seq[BoundingBox]] and
    (__ \ "properties").read[TimeMapResponseProperties]
  ) (TimeMapBoxesResponse.SingleSearchResult.apply _)

  implicit val timeMapWktResponseReads: Reads[TimeMapBoxesResponse] = (
    (__ \ "results").read[Seq[TimeMapBoxesResponse.SingleSearchResult]] and
    __.read[JsValue]
  )(TimeMapBoxesResponse.apply _)

}
