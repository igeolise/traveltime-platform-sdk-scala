package com.igeolise.traveltimesdk.dto.responses.timefilter

import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformResponse
import com.igeolise.traveltimesdk.dto.responses.common.{DistanceBreakdown, Fares, Route}
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterResponse.SingleSearchResult
import play.api.libs.json.JsValue

import scala.concurrent.duration.FiniteDuration

case class TimeFilterResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimePlatformResponse

object TimeFilterResponse {
  case class SingleSearchResult(
    searchId: String,
    locations: Seq[Location],
    unreachable: Seq[String]
  )

  case class Location(
    id: String,
    properties: Seq[Properties]
  )

  case class Properties(
    travelTime: Option[FiniteDuration] = None,
    distanceMeters: Option[Int] = None,
    distanceBreakdown: Option[DistanceBreakdown] = None,
    fares: Option[Fares] = None,
    route: Option[Route] = None
  )
}
