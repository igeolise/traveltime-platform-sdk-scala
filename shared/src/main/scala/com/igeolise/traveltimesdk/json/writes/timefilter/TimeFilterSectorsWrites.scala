package com.igeolise.traveltimesdk.json.writes.timefilter

import com.igeolise.traveltimesdk.dto.common.ZoneSearches.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterSectorsRequest
import com.igeolise.traveltimesdk.json.writes.CommonWrites._
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Writes, __}

object TimeFilterSectorsWrites {
  implicit val timeFilterSectorsWrites: Writes[TimeFilterSectorsRequest] = (
    (__ \ "departure_searches").write[Seq[DepartureSearch]] and
    (__ \ "arrival_searches").write[Seq[ArrivalSearch]]
  )(unlift(TimeFilterSectorsRequest.unapply))
}
