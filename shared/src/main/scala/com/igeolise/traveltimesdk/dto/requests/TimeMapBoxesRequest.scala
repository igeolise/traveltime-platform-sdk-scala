package com.igeolise.traveltimesdk.dto.requests

import cats.Monad
import com.igeolise.traveltimesdk.json.reads.TimeMapBoxesReads._
import com.igeolise.traveltimesdk.json.writes.TimeMapWrites._
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.requests.TimeMapRequest.{Intersection, Union}
import com.igeolise.traveltimesdk.dto.responses.{TimeMapBoxesResponse, TravelTimeSdkError}
import com.softwaremill.sttp.{HeaderNames, Request, Uri}
import play.api.libs.json.Json

case class TimeMapBoxesRequest(
  departureSearches:    Seq[TimeMapRequest.DepartureSearch],
  arrivalSearches:      Seq[TimeMapRequest.ArrivalSearch],
  unionSearches:        Seq[Union],
  intersectionSearches: Seq[Intersection]
) extends TravelTimePlatformRequest[TimeMapBoxesResponse]  {
  val endpoint = TimeMapRequest.endpoint

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, TimeMapBoxesResponse]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[TimeMapBoxesResponse]
    )

  override def sttpRequest(host: Uri): Request[String, Nothing] =
    RequestUtils.makePostRequest(
      Json.toJson(this),
      endpoint,
      host
    ).headers(HeaderNames.Accept -> "application/vnd.bounding-boxes+json")
}