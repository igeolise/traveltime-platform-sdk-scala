package com.traveltime.sdk.dto.responses

import com.traveltime.sdk.TravelTimeSDK.TravelTimeResponse
import com.traveltime.sdk.dto.responses.RoutesResponse.SingleSearchResult
import com.traveltime.sdk.dto.responses.common.{Fares, Route}
import play.api.libs.json.JsValue

import scala.concurrent.duration.FiniteDuration

case class RoutesResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimeResponse

object RoutesResponse {
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
    travelTime: Option[FiniteDuration],
    distanceMeters: Option[Int],
    route: Option[Route],
    fares: Option[Fares]
  )
}
