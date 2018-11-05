package com.igeolise.traveltimesdk.json.reads.timefilter

import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterPostcodesResponse
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterPostcodesResponse.{Postcode, PostcodesProperties}
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsValue, Reads, __}
import com.igeolise.traveltimesdk.json.reads.CommonReads._
import scala.concurrent.duration.FiniteDuration

object TimeFilterPostcodesReads  {
  implicit val postcodesPropertiesReads: Reads[PostcodesProperties] = (
    (__ \ "travel_time").readNullable[FiniteDuration] and
    (__ \ "distance").readNullable[Int]
  )(PostcodesProperties.apply _)

  implicit val postcodesReads: Reads[Postcode] = (
    (__ \ "code").read[String] and
    (__ \ "properties").read[Seq[PostcodesProperties]]
  )(Postcode.apply _)

  implicit val timeFilterPostcodesSingleSearchResultReads: Reads[TimeFilterPostcodesResponse.SingleSearchResult] = (
    (__ \ "search_id").read[String] and
    (__ \ "postcodes").read[Seq[TimeFilterPostcodesResponse.Postcode]]
  ) (TimeFilterPostcodesResponse.SingleSearchResult.apply _)

  implicit val timeFilterPostcodesResponseSuccessReads: Reads[TimeFilterPostcodesResponse] =(
    (__ \ "results").read[Seq[TimeFilterPostcodesResponse.SingleSearchResult]] and
    __.read[JsValue]
  )(TimeFilterPostcodesResponse.apply _)
}
