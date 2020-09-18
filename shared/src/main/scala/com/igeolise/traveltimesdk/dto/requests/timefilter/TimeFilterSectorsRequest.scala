package com.igeolise.traveltimesdk.dto.requests.timefilter

import com.igeolise.traveltimesdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.igeolise.traveltimesdk.dto.common.ZoneSearches.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterSectorsResponse
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterSectorsReads._
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterSectorsWrites._
import com.igeolise.traveltimesdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.Json
import sttp.client.Request

case class TimeFilterSectorsRequest(
  departureSearch: Seq[DepartureSearch],
  arrivalSearch: Seq[ArrivalSearch]
) extends TravelTimeRequest[TimeFilterSectorsResponse] {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK.createPostRequest(Json.toJson(this), host.uri.path("v4", "time-filter", "postcode-sectors"))

  final val transform: TransformFn[TimeFilterSectorsResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[TimeFilterSectorsResponse])
}