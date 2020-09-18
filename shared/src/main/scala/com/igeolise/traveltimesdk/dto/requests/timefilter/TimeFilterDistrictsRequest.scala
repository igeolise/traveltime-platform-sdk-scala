package com.igeolise.traveltimesdk.dto.requests.timefilter

import com.igeolise.traveltimesdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.igeolise.traveltimesdk.dto.common.ZoneSearches
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterDistrictsResponse
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterDistrictsReads._
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterDistrictsWrites._
import com.igeolise.traveltimesdk.{TravelTimeHost, TravelTimeSDK}
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