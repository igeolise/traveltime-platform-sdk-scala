package com.igeolise.traveltimesdk.dto.requests

import java.time.ZonedDateTime

import com.igeolise.traveltimesdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.TimeMapRequest.{Intersection, Union}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeMapRequestProperty
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.RangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.CommonTransportation
import com.igeolise.traveltimesdk.dto.responses.TimeMapResponse
import com.igeolise.traveltimesdk.json.reads.TimeMapReads._
import com.igeolise.traveltimesdk.json.writes.TimeMapWrites._
import com.igeolise.traveltimesdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.Json
import sttp.client.Request

import scala.concurrent.duration.FiniteDuration


case class TimeMapRequest(
  departureSearches:    Seq[TimeMapRequest.DepartureSearch],
  arrivalSearches:      Seq[TimeMapRequest.ArrivalSearch],
  unionSearches:        Seq[Union],
  intersectionSearches: Seq[Intersection]
) extends TravelTimeRequest[TimeMapResponse] {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK.createPostRequest(Json.toJson(this), host.uri.path("v4", "time-map"))

  final val transform: TransformFn[TimeMapResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[TimeMapResponse])
}

object TimeMapRequest {
  sealed trait LevelOfDetail

  case class SimpleLevelOfDetail(level: SimpleLevelOfDetail.Level) extends LevelOfDetail
  object SimpleLevelOfDetail {
    sealed trait Level
    case object Lowest  extends Level
    case object Low     extends Level
    case object Medium  extends Level
    case object High    extends Level
    case object Highest extends Level
  }

  sealed trait SearchType

  trait UnionOrIntersection {
    def id: String
    def searchIDs: Seq[String]
  }

  case class Union(override val id: String, override val searchIDs: Seq[String])        extends UnionOrIntersection
  case class Intersection(override val id: String, override val searchIDs: Seq[String]) extends UnionOrIntersection

  case class DepartureSearch(
    id: String,
    coordinates: Coords,
    transportation: CommonTransportation,
    departureTime: ZonedDateTime,
    travelTime: FiniteDuration,
    range: Option[RangeParams] = None,
    properties: Option[Seq[TimeMapRequestProperty]] = None,
    levelOfDetail: Option[LevelOfDetail] = None
  ) extends SearchType

  case class ArrivalSearch(
    id: String,
    coordinates: Coords,
    transportation: CommonTransportation,
    arrivalTime: ZonedDateTime,
    travelTime: FiniteDuration,
    range: Option[RangeParams] = None,
    properties: Option[Seq[TimeMapRequestProperty]] = None,
    levelOfDetail: Option[LevelOfDetail] = None
  ) extends SearchType
}

