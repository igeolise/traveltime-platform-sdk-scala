package com.igeolise.traveltimesdk.dto.responses.common

case class Fares(
  breakdown: Seq[Fares.FareBreakdown],
  ticketsTotal: Seq[Fares.Ticket]
)

object Fares {
  case class FareBreakdown(
    modes: Seq[String],
    routePartIds: Seq[Int],
    tickets: Seq[Ticket]
  )

  case class Ticket(
    ticketType: String,
    price: Double,
    currency: String
  )
}
