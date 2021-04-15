package com.traveltime.sdk.json.writes.timefilter

import java.time.ZonedDateTime

import com.traveltime.sdk.dto.requests.common.CommonProperties.{Property, TimeFilterRequestProperty}
import com.traveltime.sdk.dto.requests.common.Location
import com.traveltime.sdk.dto.requests.common.RangeParams.FullRangeParams
import com.traveltime.sdk.dto.requests.common.Transportation.CommonTransportation
import com.traveltime.sdk.dto.requests.timefilter.TimeFilterRequest
import com.traveltime.sdk.dto.requests.timefilter.TimeFilterRequest.{ArrivalSearch, DepartureSearch}
import com.traveltime.sdk.json.writes.CommonWrites._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, __}
import play.api.libs.json.Writes.iterableWrites2

import scala.concurrent.duration.FiniteDuration

object TimeFilterWrites {

  implicit val timeFilterArrivalSearchWrites: Writes[ArrivalSearch] = (
    (__ \ "id").write[String] and
    (__ \ "departure_location_ids").write[Seq[String]] and
    (__ \ "arrival_location_id").write[String] and
    (__ \ "transportation").write[CommonTransportation] and
    (__ \ "travel_time").write[FiniteDuration](finiteDurationToSecondsWrites) and
    (__ \ "arrival_time").write[ZonedDateTime] and
    (__ \ "range").writeNullable[FullRangeParams] and
    (__ \ "properties").write[Seq[Property]]
  )(unlift(ArrivalSearch.unapply))

  implicit val timeFilterDepartureSearchWrites: Writes[DepartureSearch] = (
    (__ \ "id").write[String] and
    (__ \ "departure_location_id").write[String] and
    (__ \ "arrival_location_ids").write[Seq[String]] and
    (__ \ "transportation").write[CommonTransportation] and
    (__ \ "travel_time").write[FiniteDuration](finiteDurationToSecondsWrites) and
    (__ \ "departure_time").write[ZonedDateTime] and
    (__ \ "range").writeNullable[FullRangeParams] and
    (__ \ "properties").write[Seq[Property]]
  )(unlift(DepartureSearch.unapply))

  implicit val timeFilterWrites: Writes[TimeFilterRequest] = (
    (__ \ "locations").write[Seq[Location]] and
    (__ \ "departure_searches").write[Seq[DepartureSearch]](Writes.seq(timeFilterDepartureSearchWrites)) and
    (__ \ "arrival_searches").write[Seq[ArrivalSearch]](Writes.seq(timeFilterArrivalSearchWrites))
  )(unlift(TimeFilterRequest.unapply))
}
