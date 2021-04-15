package com.traveltime.sdk.dto.requests.timefilter

import com.traveltime.sdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.traveltime.sdk.dto.common.ZoneSearches
import com.traveltime.sdk.dto.responses.timefilter.TimeFilterDistrictsResponse
import com.traveltime.sdk.json.reads.timefilter.TimeFilterDistrictsReads._
import com.traveltime.sdk.json.writes.timefilter.TimeFilterDistrictsWrites._
import com.traveltime.sdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.Json
import sttp.client.Request

case class TimeFilterDistrictsRequest (
  departureSearch: Seq[ZoneSearches.DepartureSearch],
  arrivalSearch: Seq[ZoneSearches.ArrivalSearch]
) extends TravelTimeRequest[TimeFilterDistrictsResponse]  {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK.createPostRequest(Json.toJson(this), host.uri.path("v4", "time-filter", "postcode-districts"))

  final val transform: TransformFn[TimeFilterDistrictsResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[TimeFilterDistrictsResponse])
}

object TimeFilterDistrictsRequest {
  val endpoint = "v4/time-filter/postcode-districts"
}