package com.igeolise.traveltimesdk.json.writes

import java.time.ZonedDateTime

import com.igeolise.traveltimesdk.dto.requests.RoutesRequest
import com.igeolise.traveltimesdk.dto.requests.RoutesRequest.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.{Property, RoutesRequestProperty}
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.CommonTransportation
import com.igeolise.traveltimesdk.dto.requests.common.{Location, Transportation}
import com.igeolise.traveltimesdk.json.writes.CommonWrites._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, __}

object RoutesWrites {

  implicit val routesArrivalSearchWrites: Writes[ArrivalSearch] = (
    (__ \ "id").write[String] and
    (__ \ "departure_location_ids").write[Seq[String]] and
    (__ \ "arrival_location_id").write[String] and
    (__ \ "transportation").write[CommonTransportation] and
    (__ \ "arrival_time").write[ZonedDateTime] and
    (__ \ "properties").write[Seq[Property]] and
    (__ \ "range").writeNullable[FullRangeParams]
  )(unlift(ArrivalSearch.unapply))

  implicit val routesDepartureSearchWrites: Writes[DepartureSearch] = (
    (__ \ "id").write[String] and
    (__ \ "departure_location_id").write[String] and
    (__ \ "arrival_location_ids").write[Seq[String]] and
    (__ \ "transportation").write[CommonTransportation] and
    (__ \ "departure_time").write[ZonedDateTime] and
    (__ \ "properties").write[Seq[Property]] and
    (__ \ "range").writeNullable[FullRangeParams]
  )(unlift(DepartureSearch.unapply))

  implicit val routesRequestWrites: Writes[RoutesRequest] = (
    (__ \ "locations").write[Seq[Location]] and
    (__ \ "departure_searches").write[Seq[DepartureSearch]](Writes.seq(routesDepartureSearchWrites)) and
    (__ \ "arrival_searches").write[Seq[ArrivalSearch]](Writes.seq(routesArrivalSearchWrites))
  ) (unlift(RoutesRequest.unapply))

}
