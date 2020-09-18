package com.igeolise.traveltimesdk.dto.requests.timefilter

import com.igeolise.traveltimesdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeFilterPostcodesProperty
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.CommonTransportation
import com.igeolise.traveltimesdk.dto.responses.timefilter.{TimeFilterPostcodesResponse, TimeFilterResponse}
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterPostcodesReads._
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterPostcodesWrites._
import com.igeolise.traveltimesdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.{JsResult, JsValue, Json}
import sttp.client.Request

import scala.concurrent.duration.FiniteDuration

case class TimeFilterPostcodesRequest(
  departureSearches: Seq[TimeFilterPostcodesRequest.DepartureSearch],
  arrivalSearches:   Seq[TimeFilterPostcodesRequest.ArrivalSearch]
) extends TravelTimeRequest[TimeFilterPostcodesResponse] {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK.createPostRequest(Json.toJson(this), host.uri.path("v4", "time-filter", "postcodes"))

  final val transform: TransformFn[TimeFilterPostcodesResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[TimeFilterPostcodesResponse])
}

object TimeFilterPostcodesRequest {

  sealed trait SearchType

  case class DepartureSearch(
    id: String,
    coords: Coords,
    transportation: CommonTransportation,
    departureTime: String,
    travelTime: FiniteDuration,
    rangeParams: Option[FullRangeParams],
    properties: Seq[TimeFilterPostcodesProperty]
  ) extends SearchType

  case class ArrivalSearch(
    id: String,
    coords: Coords,
    transportation: CommonTransportation,
    arrivalTime: String,
    travelTime: FiniteDuration,
    rangeParams: Option[FullRangeParams],
    properties: Seq[TimeFilterPostcodesProperty]
  ) extends SearchType
}