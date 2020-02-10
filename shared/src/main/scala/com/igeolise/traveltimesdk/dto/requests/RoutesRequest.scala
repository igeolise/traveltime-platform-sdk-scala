package com.igeolise.traveltimesdk.dto.requests

import java.time.ZonedDateTime

import cats.Monad
import com.igeolise.traveltimesdk.json.reads.RoutesReads._
import com.igeolise.traveltimesdk.json.writes.RoutesWrites._
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.requests.RoutesRequest.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.RoutesRequestProperty
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.CommonTransportation
import com.igeolise.traveltimesdk.dto.requests.common.Location
import com.igeolise.traveltimesdk.dto.responses.{RoutesResponse, TravelTimeSdkError}
import com.softwaremill.sttp.{HeaderNames, MediaTypes, Request, Uri}
import play.api.libs.json.Json

case class RoutesRequest(
  locations:         Seq[Location],
  departureSearches: Seq[DepartureSearch],
  arrivalSearches:   Seq[ArrivalSearch]
) extends TravelTimePlatformRequest[RoutesResponse] {
  val endpoint = "v4/routes"

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, RoutesResponse]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[RoutesResponse]
    )

  override def sttpRequest(host: Uri): Request[String, Nothing] =
    RequestUtils.makePostRequest(
      Json.toJson(this),
      endpoint,
      host
    ).headers(HeaderNames.Accept -> MediaTypes.Json)
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
