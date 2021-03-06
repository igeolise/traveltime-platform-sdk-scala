package com.traveltime.sdk.json.writes.timefilter

import com.traveltime.sdk.dto.common.Coords
import com.traveltime.sdk.dto.requests.timefilter.TimeFilterPostcodesRequest
import com.traveltime.sdk.dto.requests.timefilter.TimeFilterPostcodesRequest.{ArrivalSearch, DepartureSearch}
import com.traveltime.sdk.dto.requests.common.CommonProperties.{Property, TimeFilterPostcodesProperty}
import com.traveltime.sdk.dto.requests.common.RangeParams.FullRangeParams
import com.traveltime.sdk.dto.requests.common.Transportation
import com.traveltime.sdk.dto.requests.common.Transportation.CommonTransportation
import com.traveltime.sdk.json.writes.CommonWrites._
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
