package com.igeolise.traveltimesdk.dto.requests.timefilter

import cats.Monad
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterPostcodesReads._
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterPostcodesWrites._
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.RequestUtils
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeFilterPostcodesProperty
import com.igeolise.traveltimesdk.dto.requests.common.CommonTransportation
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterPostcodesResponse
import com.softwaremill.sttp._
import play.api.libs.json.Json
import scala.concurrent.duration.FiniteDuration

case class TimeFilterPostcodesRequest(
  departureSearches: Seq[TimeFilterPostcodesRequest.DepartureSearch],
  arrivalSearches:   Seq[TimeFilterPostcodesRequest.ArrivalSearch]
) extends TravelTimePlatformRequest[TimeFilterPostcodesResponse] {

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, TimeFilterPostcodesResponse]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[TimeFilterPostcodesResponse]
    )

  override def sttpRequest(host: Uri): Request[String, Nothing] =
    RequestUtils.makePostRequest(
      Json.toJson(this),
      "v4/time-filter/postcodes",
      host
    ).headers(HeaderNames.Accept -> MediaTypes.Json)
}

object TimeFilterPostcodesRequest {
  sealed trait SearchType
  sealed case class DepartureSearch(
    id: String,
    coords: Coords,
    transportation: CommonTransportation,
    departureTime: String,
    travelTime: FiniteDuration,
    rangeParams: Option[FullRangeParams],
    properties: Seq[TimeFilterPostcodesProperty]
  ) extends SearchType

  sealed case class ArrivalSearch(
    id: String,
    coords: Coords,
    transportation: CommonTransportation,
    arrivalTime: String,
    travelTime: FiniteDuration,
    rangeParams: Option[FullRangeParams],
    properties: Seq[TimeFilterPostcodesProperty]
  ) extends SearchType
}