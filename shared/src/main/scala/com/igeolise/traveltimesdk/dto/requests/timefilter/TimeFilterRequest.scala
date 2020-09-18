package com.igeolise.traveltimesdk.dto.requests.timefilter

import java.time.ZonedDateTime

import com.igeolise.traveltimesdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeFilterRequestProperty
import com.igeolise.traveltimesdk.dto.requests.common.Location
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.CommonTransportation
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterResponse
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterReads._
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterWrites._
import com.igeolise.traveltimesdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.Json
import sttp.client.Request

import scala.concurrent.duration.FiniteDuration

case class TimeFilterRequest(
  locations: Seq[Location],
  departureSearches: Seq[TimeFilterRequest.DepartureSearch],
  arrivalSearches:   Seq[TimeFilterRequest.ArrivalSearch]
) extends TravelTimeRequest[TimeFilterResponse] {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK.createPostRequest(Json.toJson(this), host.uri.path("v4", "time-filter"))

  final val transform: TransformFn[TimeFilterResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[TimeFilterResponse])
}

object TimeFilterRequest {

  sealed trait SearchType

  case class DepartureSearch(
    id: String,
    departureLocationId: String,
    arrivalLocationIds: Seq[String],
    transportation: CommonTransportation,
    travelTime: FiniteDuration,
    departureTime: ZonedDateTime,
    range: Option[FullRangeParams],
    properties: Seq[TimeFilterRequestProperty]
  ) extends SearchType

  case class ArrivalSearch(
    id: String,
    departureLocationIds: Seq[String],
    arrivalLocationId: String,
    transportation: CommonTransportation,
    travelTime: FiniteDuration,
    arrivalTime: ZonedDateTime,
    range: Option[FullRangeParams],
    properties: Seq[TimeFilterRequestProperty]
  ) extends SearchType
}


