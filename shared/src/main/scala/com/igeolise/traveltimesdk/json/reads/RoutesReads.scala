package com.igeolise.traveltimesdk.json.reads

import com.igeolise.traveltimesdk.dto.responses.RoutesResponse
import com.igeolise.traveltimesdk.dto.responses.common.{Fares, Route}
import play.api.libs.functional.syntax._
import com.igeolise.traveltimesdk.json.reads.CommonReads._
import play.api.libs.json.{JsValue, Reads, __}
import scala.concurrent.duration.FiniteDuration

object RoutesReads {

  implicit val routePropertiesReads: Reads[RoutesResponse.Properties] = (
    (__ \ "travel_time").readNullable[FiniteDuration](secondsToFiniteDurationReads) and
    (__ \ "distance").readNullable[Int] and
    (__ \ "route").readNullable[Route] and
    (__ \ "fares").readNullable[Fares]
  )(RoutesResponse.Properties.apply _)

  implicit val routeLocationReads: Reads[RoutesResponse.Location] = (
    (__ \ "id").read[String] and
    (__ \ "properties").read[Seq[RoutesResponse.Properties]]
  ) (RoutesResponse.Location.apply _)

  implicit val routeSingleSearchResultReads: Reads[RoutesResponse.SingleSearchResult] = (
    (__ \ "search_id").read[String] and
    (__ \ "locations").read[Seq[RoutesResponse.Location]] and
    (__ \ "unreachable").read[Seq[String]]
  ) (RoutesResponse.SingleSearchResult.apply _)

  implicit val RoutesResultReadsV4: Reads[RoutesResponse] =(
    (__ \ "results").read[Seq[RoutesResponse.SingleSearchResult]] and
    __.read[JsValue]
  )(RoutesResponse.apply _)
}