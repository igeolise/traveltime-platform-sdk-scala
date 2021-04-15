package com.traveltime.sdk.dto.requests.timefilter

import com.traveltime.sdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.traveltime.sdk.dto.common.Coords
import com.traveltime.sdk.dto.requests.common.CommonProperties.TimeFilterPostcodesProperty
import com.traveltime.sdk.dto.requests.common.RangeParams.FullRangeParams
import com.traveltime.sdk.dto.requests.common.Transportation.CommonTransportation
import com.traveltime.sdk.dto.responses.timefilter.{TimeFilterPostcodesResponse, TimeFilterResponse}
import com.traveltime.sdk.json.reads.timefilter.TimeFilterPostcodesReads._
import com.traveltime.sdk.json.writes.timefilter.TimeFilterPostcodesWrites._
import com.traveltime.sdk.{TravelTimeHost, TravelTimeSDK}
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