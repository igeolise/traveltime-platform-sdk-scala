package com.igeolise.traveltimesdk.dto.responses.timefilter

import com.igeolise.traveltimesdk.TravelTimeSDK.TravelTimeResponse
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterFastResponse.SingleSearchResult
import play.api.libs.json.JsValue

import scala.concurrent.duration.FiniteDuration

case class TimeFilterFastResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimeResponse

object TimeFilterFastResponse {
  case class SingleSearchResult(
    searchId: String,
    locations: Seq[Location],
    unreachable: Seq[String]
  )

  case class Location(
    id: String,
    properties: Properties
  )

  case class Properties(
    travelTime: Option[FiniteDuration],
    fares: Fares
  )

  case class Fares(ticketsTotal: Seq[Ticket])

  case class Ticket(
    `type`: String,
    price: Double,
    currency: String
  )
}