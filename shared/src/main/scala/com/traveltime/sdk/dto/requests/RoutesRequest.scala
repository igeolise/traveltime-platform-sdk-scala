package com.traveltime.sdk.dto.requests

import java.time.ZonedDateTime

import com.traveltime.sdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.traveltime.sdk.dto.requests.RoutesRequest.{ArrivalSearch, DepartureSearch}
import com.traveltime.sdk.dto.requests.common.CommonProperties.RoutesRequestProperty
import com.traveltime.sdk.dto.requests.common.Location
import com.traveltime.sdk.dto.requests.common.RangeParams.FullRangeParams
import com.traveltime.sdk.dto.requests.common.Transportation.CommonTransportation
import com.traveltime.sdk.dto.responses.RoutesResponse
import com.traveltime.sdk.json.reads.RoutesReads._
import com.traveltime.sdk.json.writes.RoutesWrites._
import com.traveltime.sdk.{TravelTimeHost, TravelTimeSDK}
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
