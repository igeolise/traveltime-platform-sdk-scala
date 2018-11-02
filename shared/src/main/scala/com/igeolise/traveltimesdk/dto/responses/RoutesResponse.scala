package com.igeolise.traveltimesdk.dto.responses

import com.igeolise.traveltimesdk.dto.responses.RoutesResponse.SingleSearchResult
import com.igeolise.traveltimesdk.dto.responses.common.{Fares, Route}
import play.api.libs.json.JsValue

case class RoutesResponse(results: Seq[SingleSearchResult], raw: JsValue)

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
    travelTimeSeconds: Option[Int],
    distanceMeters: Option[Int],
    route: Option[Route],
    fares: Option[Fares]
  )
}
