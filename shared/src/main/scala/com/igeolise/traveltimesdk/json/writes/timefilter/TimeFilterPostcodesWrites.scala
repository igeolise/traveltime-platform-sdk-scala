package com.igeolise.traveltimesdk.json.writes.timefilter

import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterPostcodesRequest
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterPostcodesRequest.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeFilterPostcodesProperty
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation
import com.igeolise.traveltimesdk.json.writes.CommonWrites._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, __}

object TimeFilterPostcodesWrites {
  implicit val timeFilterPostcodesDepartureWrites: Writes[TimeFilterPostcodesRequest.DepartureSearch] = (
    (__ \ "id").write[String] and
    (__ \ "coords").write[Coords] and
    (__ \ "transportation").write[Transportation] and
    (__ \ "departure_time").write[String] and
    (__ \ "travel_time").write[Int] and
    (__ \ "range").writeNullable[FullRangeParams] and
    (__ \ "properties").write[Seq[TimeFilterPostcodesProperty]]
  )(unlift(DepartureSearch.unapply))

  implicit val timeFilterPostcodesArrivalWrites: Writes[TimeFilterPostcodesRequest.ArrivalSearch] = (
    (__ \ "id").write[String] and
    (__ \ "coords").write[Coords] and
    (__ \ "transportation").write[Transportation] and
    (__ \ "arrival_time").write[String] and
    (__ \ "travel_time").write[Int] and
    (__ \ "range").writeNullable[FullRangeParams] and
    (__ \ "properties").write[Seq[TimeFilterPostcodesProperty]]
  )(unlift(ArrivalSearch.unapply))

  implicit val timeFilterPostcodesWrites: Writes[TimeFilterPostcodesRequest] = (
    (__ \ "departure_searches").write[Seq[DepartureSearch]](Writes.seq(timeFilterPostcodesDepartureWrites)) and
    (__ \ "arrival_searches").write[Seq[ArrivalSearch]](Writes.seq(timeFilterPostcodesArrivalWrites))
  )(unlift(TimeFilterPostcodesRequest.unapply))
}
