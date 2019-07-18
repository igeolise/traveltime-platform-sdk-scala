package com.igeolise.traveltimesdk.dto.requests

import cats.Monad
import com.igeolise.traveltimesdk.json.reads.TimeMapReads._
import com.igeolise.traveltimesdk.json.writes.TimeMapWrites._
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.requests.TimeMapRequest.{Intersection, Union}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeMapRequestProperty
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.RangeParams
import com.igeolise.traveltimesdk.dto.responses.{TimeMapResponse, TravelTimeSdkError}
import com.softwaremill.sttp._
import play.api.libs.json._
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.CommonTransportation

import scala.concurrent.duration.FiniteDuration
import java.time.ZonedDateTime


case class TimeMapRequest(
  departureSearches:    Seq[TimeMapRequest.DepartureSearch],
  arrivalSearches:      Seq[TimeMapRequest.ArrivalSearch],
  unionSearches:        Seq[Union],
  intersectionSearches: Seq[Intersection]
) extends TravelTimePlatformRequest[TimeMapResponse] {

  override def sttpRequest(host: Uri): Request[String, Nothing] = {
    RequestUtils.makePostRequest(
      Json.toJson(this),
      "v4/time-map",
      host
    ).headers(HeaderNames.Accept -> MediaTypes.Json)
  }

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, TimeMapResponse]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[TimeMapResponse]
    )
}

object TimeMapRequest {
  sealed trait SearchType

  trait UnionOrIntersection {
    def id: String
    def searchIDs: Seq[String]
  }

  case class Union(override val id: String, override val searchIDs: Seq[String])        extends UnionOrIntersection
  case class Intersection(override val id: String, override val searchIDs: Seq[String]) extends UnionOrIntersection

  sealed case class DepartureSearch(
    id: String,
    coordinates: Coords,
    transportation: CommonTransportation,
    departureTime: ZonedDateTime,
    travelTime: FiniteDuration,
    range: Option[RangeParams] = None,
    properties: Option[Seq[TimeMapRequestProperty]] = None
  ) extends SearchType

  sealed case class ArrivalSearch(
    id: String,
    coordinates: Coords,
    transportation: CommonTransportation,
    arrivalTime: ZonedDateTime,
    travelTime: FiniteDuration,
    range: Option[RangeParams] = None,
    properties: Option[Seq[TimeMapRequestProperty]] = None
  ) extends SearchType
}

