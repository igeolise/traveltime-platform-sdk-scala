package com.igeolise.traveltimesdk.json.writes.timefilter

import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterPostcodesRequest
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterPostcodesRequest.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.Property
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.CommonTransportation
import com.igeolise.traveltimesdk.json.writes.CommonWrites._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, __}

import scala.concurrent.duration.FiniteDuration

object TimeFilterPostcodesWrites {

  implicit val timeFilterPostcodesDepartureWrites: Writes[TimeFilterPostcodesRequest.DepartureSearch] = (
    (__ \ "id").write[String] and
    (__ \ "coords").write[Coords] and
    (__ \ "transportation").write[CommonTransportation] and
    (__ \ "departure_time").write[String] and
    (__ \ "travel_time").write[FiniteDuration](finiteDurationToSecondsWrites) and
    (__ \ "range").writeNullable[FullRangeParams] and
    (__ \ "properties").write[Seq[Property]]
  )(unlift(DepartureSearch.unapply))

  implicit val timeFilterPostcodesArrivalWrites: Writes[TimeFilterPostcodesRequest.ArrivalSearch] = (
    (__ \ "id").write[String] and
    (__ \ "coords").write[Coords] and
    (__ \ "transportation").write[CommonTransportation] and
    (__ \ "arrival_time").write[String] and
    (__ \ "travel_time").write[FiniteDuration](finiteDurationToSecondsWrites) and
    (__ \ "range").writeNullable[FullRangeParams] and
    (__ \ "properties").write[Seq[Property]]
  )(unlift(ArrivalSearch.unapply))

  implicit val timeFilterPostcodesWrites: Writes[TimeFilterPostcodesRequest] = (
    (__ \ "departure_searches").write[Seq[DepartureSearch]](Writes.seq(timeFilterPostcodesDepartureWrites)) and
    (__ \ "arrival_searches").write[Seq[ArrivalSearch]](Writes.seq(timeFilterPostcodesArrivalWrites))
  )(unlift(TimeFilterPostcodesRequest.unapply))
}
