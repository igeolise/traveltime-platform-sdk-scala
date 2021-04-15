package com.traveltime.sdk.dto.requests.timefilter

import com.traveltime.sdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.traveltime.sdk.dto.common.ZoneSearches.{ArrivalSearch, DepartureSearch}
import com.traveltime.sdk.dto.responses.timefilter.TimeFilterSectorsResponse
import com.traveltime.sdk.json.reads.timefilter.TimeFilterSectorsReads._
import com.traveltime.sdk.json.writes.timefilter.TimeFilterSectorsWrites._
import com.traveltime.sdk.{TravelTimeHost, TravelTimeSDK}
import com.traveltime.sdk.{TravelTimeHost, TravelTimeSDK}
import com.traveltime.sdk.TravelTimeSDK.{ResponseBody, TravelTimeRequest}
import com.traveltime.sdk.dto.common.ZoneSearches.{ArrivalSearch, DepartureSearch}
import com.traveltime.sdk.dto.responses.timefilter.TimeFilterSectorsResponse
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