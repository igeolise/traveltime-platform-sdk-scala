package com.igeolise.traveltimesdk.dto.requests

import com.igeolise.traveltimesdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.igeolise.traveltimesdk.dto.requests.common.Location
import com.igeolise.traveltimesdk.dto.responses.SupportedLocationsResponse
import com.igeolise.traveltimesdk.json.reads.AvailableDataReads._
import com.igeolise.traveltimesdk.json.writes.SupportedLocationsWrites._
import com.igeolise.traveltimesdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.Json
import sttp.client.Request

case class SupportedLocationsRequest(locations: Seq[Location]) extends TravelTimeRequest[SupportedLocationsResponse] {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK.createPostRequest(Json.toJson(this), host.uri.path("v4", "supported-locations"))

  final val transform: TransformFn[SupportedLocationsResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[SupportedLocationsResponse])
}
