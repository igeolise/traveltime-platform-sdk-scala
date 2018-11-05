package com.igeolise.traveltimesdk.dto.responses.timefilter

import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterFastResponse.SingleSearchResult
import play.api.libs.json.JsValue
import scala.concurrent.duration.FiniteDuration

case class TimeFilterFastResponse(results: Seq[SingleSearchResult], raw: JsValue)

object TimeFilterFastResponse {
  sealed case class SingleSearchResult(
    searchId: String,
    locations: Seq[Location],
    unreachable: Seq[String]
  )

  sealed case class Location(
    id: String,
    properties: Properties
  )

  sealed case class Properties(
    travelTime: Option[FiniteDuration],
    fares: Fares
  )

  sealed case class Fares(ticketsTotal: Seq[Ticket])

  sealed case class Ticket(
    `type`: String,
    price: Double,
    currency: String
  )
}