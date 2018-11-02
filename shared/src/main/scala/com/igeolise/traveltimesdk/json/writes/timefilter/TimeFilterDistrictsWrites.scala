package com.igeolise.traveltimesdk.json.writes.timefilter

import com.igeolise.traveltimesdk.json.writes.CommonWrites._
import com.igeolise.traveltimesdk.dto.common.ZoneSearches.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterDistrictsRequest
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Writes, __}

object TimeFilterDistrictsWrites {
  implicit val timeFilterDistrictsWrites: Writes[TimeFilterDistrictsRequest] = (
    (__ \ "departure_searches").write[Seq[DepartureSearch]](Writes.seq(timeFilterZonesDepartureWrites)) and
    (__ \ "arrival_searches").write[Seq[ArrivalSearch]](Writes.seq(timeFilterZonesArrivalWrites))
  )(unlift(TimeFilterDistrictsRequest.unapply))
}
