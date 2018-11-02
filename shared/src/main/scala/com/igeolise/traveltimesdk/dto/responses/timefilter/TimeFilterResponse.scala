package com.igeolise.traveltimesdk.dto.responses.timefilter

import com.igeolise.traveltimesdk.dto.responses.common.{Fares, Route}
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterResponse.SingleSearchResult
import play.api.libs.json.JsValue

case class TimeFilterResponse(results: Seq[SingleSearchResult], raw: JsValue)

object TimeFilterResponse {
  sealed case class SingleSearchResult(
    searchId: String,
    locations: Seq[Location],
    unreachable: Seq[String]
  )

  sealed case class Location(
    id: String,
    properties: Seq[Properties]
  )

  sealed case class Properties(
    travelTimeSeconds: Option[Int] = None,
    distanceMeters: Option[Int] = None,
    fares: Option[Fares] = None,
    route: Option[Route] = None
  )
}
