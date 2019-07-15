package com.igeolise.traveltimesdk.dto.requests

import cats.Monad
import com.igeolise.traveltimesdk.json.reads.TimeMapWktReads._
import com.igeolise.traveltimesdk.json.writes.TimeMapWrites._
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.requests.TimeMapRequest.{Intersection, Union}
import com.igeolise.traveltimesdk.dto.requests.TimeMapWktRequest.{HolesSetting, NoHoles, WithHoles}
import com.igeolise.traveltimesdk.dto.responses.{TimeMapWktResponse, TravelTimeSdkError}
import com.softwaremill.sttp.{HeaderNames, Request, Uri}
import play.api.libs.json.Json

case class TimeMapWktRequest(
  departureSearches:    Seq[TimeMapRequest.DepartureSearch],
  arrivalSearches:      Seq[TimeMapRequest.ArrivalSearch],
  unionSearches:        Seq[Union],
  intersectionSearches: Seq[Intersection]
)(holesSetting: HolesSetting = WithHoles)
  extends TravelTimePlatformRequest[TimeMapWktResponse] {

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, TimeMapWktResponse]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[TimeMapWktResponse]
    )

  override def sttpRequest(host: Uri): Request[String, Nothing] = {
    def req = RequestUtils.makePostRequest(
      Json.toJson(this),
      "v4/time-map",
      host
    )

    holesSetting match {
      case WithHoles => req.headers(HeaderNames.Accept -> "application/vnd.wkt+json")
      case NoHoles => req.headers(HeaderNames.Accept -> "application/vnd.wkt-no-holes+json")
    }
  }
}

object TimeMapWktRequest {
  sealed abstract class HolesSetting
  case object WithHoles extends HolesSetting
  case object NoHoles extends HolesSetting
}
