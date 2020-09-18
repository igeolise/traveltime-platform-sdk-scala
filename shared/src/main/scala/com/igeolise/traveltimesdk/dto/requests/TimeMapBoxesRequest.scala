package com.igeolise.traveltimesdk.dto.requests

import com.igeolise.traveltimesdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.igeolise.traveltimesdk.dto.requests.TimeMapRequest.{Intersection, Union}
import com.igeolise.traveltimesdk.dto.responses.TimeMapBoxesResponse
import com.igeolise.traveltimesdk.json.reads.TimeMapBoxesReads._
import com.igeolise.traveltimesdk.json.writes.TimeMapWrites._
import com.igeolise.traveltimesdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.Json
import sttp.client.Request
import sttp.model.{Header, HeaderNames}

case class TimeMapBoxesRequest(
  departureSearches:    Seq[TimeMapRequest.DepartureSearch],
  arrivalSearches:      Seq[TimeMapRequest.ArrivalSearch],
  unionSearches:        Seq[Union],
  intersectionSearches: Seq[Intersection]
) extends TravelTimeRequest[TimeMapBoxesResponse]  {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK
      .createPostRequest(Json.toJson(this), host.uri.path("v4", "time-map"))
      .headers(new Header(HeaderNames.Accept, "application/vnd.bounding-boxes+json"))

  final val transform: TransformFn[TimeMapBoxesResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[TimeMapBoxesResponse])
}