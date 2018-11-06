package com.igeolise.traveltimesdk.json.writes

import java.time.ZonedDateTime
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.TimeMapRequest._
import com.igeolise.traveltimesdk.dto.requests._
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeMapRequestProperty
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.RangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation
import com.igeolise.traveltimesdk.json.writes.CommonWrites._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, __}
import scala.concurrent.duration.FiniteDuration

object TimeMapWrites {

  implicit val departureSearchWrites: Writes[DepartureSearch] = (
    (__ \ "id").write[String] and
    (__ \ "coords").write[Coords] and
    (__ \ "transportation").write[Transportation] and
    (__ \ "departure_time").write[ZonedDateTime] and
    (__ \ "travel_time").write[FiniteDuration](secondsToFiniteDurationWrites) and
    (__ \ "range").writeNullable[RangeParams] and
    (__ \ "properties").writeNullable[Seq[TimeMapRequestProperty]]
  ) (unlift(DepartureSearch.unapply))

  implicit val arrivalSearchWrites: Writes[ArrivalSearch] = (
    (__ \ "id").write[String] and
    (__ \ "coords").write[Coords] and
    (__ \ "transportation").write[Transportation] and
    (__ \ "arrival_time").write[ZonedDateTime] and
    (__ \ "travel_time").write[FiniteDuration](secondsToFiniteDurationWrites) and
    (__ \ "range").writeNullable[RangeParams] and
    (__ \ "properties").writeNullable[Seq[TimeMapRequestProperty]]
  ) (unlift(ArrivalSearch.unapply))

  implicit val unionSearchWrites: Writes[Union] = (
    (__ \ "id").write[String] and
    (__ \ "search_ids").write[Seq[String]]
  ) (unlift(Union.unapply))

  implicit val intersectionSearchWrites: Writes[Intersection] = (
    (__ \ "id").write[String] and
    (__ \ "search_ids").write[Seq[String]]
  ) (unlift(Intersection.unapply))

  implicit val timeMapRequestWrites: Writes[TimeMapRequest] = (
    (__ \ "departure_searches").write[Seq[DepartureSearch]] and
    (__ \ "arrival_searches").write[Seq[ArrivalSearch]] and
    (__ \ "unions").write[Seq[Union]] and
    (__ \ "intersections").write[Seq[Intersection]]
  ) (unlift(TimeMapRequest.unapply))

  implicit val timeMapWktRequestWrites: Writes[TimeMapWktRequest] = (
    (__ \ "departure_searches").write[Seq[DepartureSearch]] and
    (__ \ "arrival_searches").write[Seq[ArrivalSearch]] and
    (__ \ "unions").write[Seq[Union]] and
    (__ \ "intersections").write[Seq[Intersection]]
  ) (unlift(TimeMapWktRequest.unapply))

  implicit val timeMapBoxesRequestWrites: Writes[TimeMapBoxesRequest] = (
    (__ \ "departure_searches").write[Seq[DepartureSearch]] and
    (__ \ "arrival_searches").write[Seq[ArrivalSearch]] and
    (__ \ "unions").write[Seq[Union]] and
    (__ \ "intersections").write[Seq[Intersection]]
  ) (unlift(TimeMapBoxesRequest.unapply))
}