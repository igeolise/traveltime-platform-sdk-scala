package com.traveltime.sdk.dto.requests

import com.traveltime.sdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.traveltime.sdk.dto.requests.TimeMapRequest.{Intersection, Union}
import com.traveltime.sdk.dto.requests.TimeMapWktRequest.{HolesSetting, WithHoles}
import com.traveltime.sdk.dto.responses.TimeMapWktResponse
import com.traveltime.sdk.json.reads.TimeMapWktReads._
import com.traveltime.sdk.json.writes.TimeMapWrites._
import com.traveltime.sdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.Json
import sttp.client.Request
import sttp.model.{Header, HeaderNames}

case class TimeMapWktRequest(
  departureSearches:    Seq[TimeMapRequest.DepartureSearch],
  arrivalSearches:      Seq[TimeMapRequest.ArrivalSearch],
  unionSearches:        Seq[Union],
  intersectionSearches: Seq[Intersection]
)(holesSetting: HolesSetting = WithHoles) extends TravelTimeRequest[TimeMapWktResponse] {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK
      .createPostRequest(Json.toJson(this), host.uri.path("v4", "time-map"))
      .headers(new Header(HeaderNames.Accept, holesSetting.headerValue))

  final val transform: TransformFn[TimeMapWktResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[TimeMapWktResponse])
}

object TimeMapWktRequest {
  sealed abstract class HolesSetting(val headerValue: String)
  case object WithHoles extends HolesSetting("application/vnd.wkt+json")
  case object NoHoles extends HolesSetting("application/vnd.wkt-no-holes+json")
}
