package com.igeolise.traveltimesdk.json.reads.timefilter

import com.igeolise.traveltimesdk.dto.responses.common.{Fares, Route}
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterResponse
import com.igeolise.traveltimesdk.json.reads.CommonReads._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsValue, Reads, __}
import scala.concurrent.duration.FiniteDuration

object TimeFilterReads {

  implicit val timeFilterPropertiesReads: Reads[TimeFilterResponse.Properties] = (
    (__ \ "travel_time").readNullable[FiniteDuration](secondsToFiniteDurationReads) and
    (__ \ "distance").readNullable[Int] and
    (__ \ "fares").readNullable[Fares] and
    (__ \ "route").readNullable[Route]
  )(TimeFilterResponse.Properties.apply _)

  implicit val timeFilterLocationReads: Reads[TimeFilterResponse.Location] = (
    (__ \ "id").read[String] and
    (__ \ "properties").read[Seq[TimeFilterResponse.Properties]]
  ) (TimeFilterResponse.Location.apply _)

  implicit val timeFilterSingleSearchResultReads: Reads[TimeFilterResponse.SingleSearchResult] = (
    (__ \ "search_id").read[String] and
    (__ \ "locations").read[Seq[TimeFilterResponse.Location]] and
    (__ \ "unreachable").read[Seq[String]]
  ) (TimeFilterResponse.SingleSearchResult.apply _)

  implicit val timeFilterResponseSuccessReads: Reads[TimeFilterResponse] =(
    (__ \ "results").read[Seq[TimeFilterResponse.SingleSearchResult]] and
    __.read[JsValue]
  )(TimeFilterResponse.apply _)
}
