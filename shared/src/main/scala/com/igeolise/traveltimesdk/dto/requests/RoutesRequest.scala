package com.igeolise.traveltimesdk.dto.requests

import java.time.ZonedDateTime

import com.igeolise.traveltimesdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.igeolise.traveltimesdk.dto.requests.RoutesRequest.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.RoutesRequestProperty
import com.igeolise.traveltimesdk.dto.requests.common.Location
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.CommonTransportation
import com.igeolise.traveltimesdk.dto.responses.RoutesResponse
import com.igeolise.traveltimesdk.json.reads.RoutesReads._
import com.igeolise.traveltimesdk.json.writes.RoutesWrites._
import com.igeolise.traveltimesdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.Json
import sttp.client.Request

case class RoutesRequest(
  locations:         Seq[Location],
  departureSearches: Seq[DepartureSearch],
  arrivalSearches:   Seq[ArrivalSearch]
) extends TravelTimeRequest[RoutesResponse] {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK.createPostRequest(Json.toJson(this), host.uri.path("v4", "routes"))

  final val transform: TransformFn[RoutesResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[RoutesResponse])
}

object RoutesRequest {

  sealed trait SearchType

  case class DepartureSearch(
    id: String,
    departureLocationId: String,
    arrivalLocationIds: Seq[String],
    transportation: CommonTransportation,
    departureTime: ZonedDateTime,
    properties: Seq[RoutesRequestProperty],
    range: Option[FullRangeParams] = None
  ) extends SearchType

  case class ArrivalSearch(
    id: String,
    departureLocationIds: Seq[String],
    arrivalLocationId: String,
    transportation: CommonTransportation,
    arrivalTime: ZonedDateTime,
    properties: Seq[RoutesRequestProperty],
    range: Option[FullRangeParams] = None
  ) extends SearchType
}
