package com.traveltime.sdk.dto.requests

import com.traveltime.sdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.traveltime.sdk.dto.requests.common.Location
import com.traveltime.sdk.dto.responses.SupportedLocationsResponse
import com.traveltime.sdk.json.reads.AvailableDataReads._
import com.traveltime.sdk.json.writes.SupportedLocationsWrites._
import com.traveltime.sdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.Json
import sttp.client.Request

case class SupportedLocationsRequest(locations: Seq[Location]) extends TravelTimeRequest[SupportedLocationsResponse] {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK.createPostRequest(Json.toJson(this), host.uri.path("v4", "supported-locations"))

  final val transform: TransformFn[SupportedLocationsResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[SupportedLocationsResponse])
}
