package com.igeolise.traveltimesdk.json.reads

import java.time.ZonedDateTime

import com.igeolise.traveltimesdk.dto.common.{Coords, TravelTime, Zone, ZoneSearchProperties}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeMapProps.TimeMapResponseProperties
import com.igeolise.traveltimesdk.dto.responses.common.Route.RoutePart
import com.igeolise.traveltimesdk.dto.responses.common.Route.RoutePart.{BasicRoutePart, PublicTransportRoutePart}
import com.igeolise.traveltimesdk.dto.responses.common.{Fares, Route}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}

import scala.concurrent.duration.{Duration, FiniteDuration, SECONDS}

object CommonReads {

  implicit val zonedTimeReads: Reads[ZonedDateTime] =
    Reads.StringReads.map(date => ZonedDateTime.parse(date))

  val secondsToFiniteDurationReads: Reads[FiniteDuration] =
    Reads.IntReads.map(time => Duration(time, SECONDS))

  implicit val timeMapResponsePropertiesReads: Reads[TimeMapResponseProperties] =
    (__ \ "is_only_walking").readNullable[Boolean].map(TimeMapResponseProperties.apply)

  implicit val coordsReads: Reads[Coords] = (
    (__ \ "lat").read[Double] and
    (__ \ "lng").read[Double]
  ) (Coords)

  implicit val ticketReads: Reads[Fares.Ticket] = (
    (__ \ "type").read[String] and
    (__ \ "price").read[Double] and
    (__ \ "currency").read[String]
  ) (Fares.Ticket.apply _)

  implicit val fareBreakdownReads: Reads[Fares.FareBreakdown] = (
    (__ \ "modes").read[Seq[String]] and
    (__ \ "route_part_ids").read[Seq[Int]] and
    (__ \ "tickets").read[Seq[Fares.Ticket]]
  ) (Fares.FareBreakdown.apply _)

  implicit val faresReads: Reads[Fares] = (
    (__ \ "breakdown").read[Seq[Fares.FareBreakdown]] and
    (__ \ "tickets_total").read[Seq[Fares.Ticket]]
  ) (Fares.apply _)

  implicit val basicRoutePartReads: Reads[BasicRoutePart] = (
    (__ \ "id").read[Int] and
    (__ \ "type").read[String] and
    (__ \ "mode").read[String] and
    (__ \ "directions").read[String] and
    (__ \ "distance").read[Int] and
    (__ \ "travel_time").read[FiniteDuration](secondsToFiniteDurationReads) and
    (__ \ "coords").read[Seq[Coords]](Reads.seq(coordsReads))
  ) (BasicRoutePart.apply _)

  val startEndRoutePartReads: Reads[Route.RoutePart.StartEndRoutePart] = (
    __.read[Route.RoutePart.BasicRoutePart] and
    (__ \ "direction").read[String]
  ) ((basic: Route.RoutePart.BasicRoutePart, direction: String) =>
     Route.RoutePart.StartEndRoutePart(
         basic.id, basic.`type`, basic.mode, basic.directions, basic.distanceMeters, basic.travelTime, basic.coords,
         direction
     )
  )
  val roadRoutePartReads: Reads[Route.RoutePart.RoadRoutePart] = (
    __.read[Route.RoutePart.BasicRoutePart] and
    (__ \ "road").readNullable[String] and
    (__ \ "turn").readNullable[String]
  )( (basic: Route.RoutePart.BasicRoutePart, road: Option[String], turn: Option[String]) =>
      Route.RoutePart.RoadRoutePart(
        basic.id, basic.`type`, basic.mode, basic.directions, basic.distanceMeters, basic.travelTime, basic.coords,
        road, turn
      )
  )

    val publicTransportRoutePartReads: Reads[PublicTransportRoutePart] = (
      __.read[Route.RoutePart.BasicRoutePart] and
      (__ \ "line").read[String] and
      (__ \ "departure_station").read[String] and
      (__ \ "arrival_station").read[String] and
      (__ \ "departs_at").read[String] and
      (__ \ "arrives_at").read[String] and
      (__ \ "num_stops").read[Int]
    ) ((basic: Route.RoutePart.BasicRoutePart, line: String, departureStation: String, arrivalStation: String, departsAt: String, arrivesAt: String, numStops: Int) =>
      PublicTransportRoutePart(
          basic.id, basic.`type`, basic.mode, basic.directions, basic.distanceMeters, basic.travelTime, basic.coords,
          line, departureStation, arrivalStation, departsAt, arrivesAt, numStops
      )
    )

  implicit val routePartReads: Reads[RoutePart] =
    (__ \ "type").read[String].flatMap {
      case "basic"            => basicRoutePartReads.map(x => x: RoutePart)
      case "start_end"        => startEndRoutePartReads.map(x => x: RoutePart)
      case "road"             => roadRoutePartReads.map(x => x: RoutePart)
      case "public_transport" => publicTransportRoutePartReads.map(x => x: RoutePart)
    }

  implicit val routeReads: Reads[Route] = (
    (__ \ "departure_time").read[String] and
    (__ \ "arrival_time").read[String] and
    (__ \ "parts").read[Seq[Route.RoutePart]]
  ) (Route.apply _)

  implicit val zoneTravelTimeReads: Reads[TravelTime] = (
    (__ \ "min").read[FiniteDuration](secondsToFiniteDurationReads) and
    (__ \ "max").read[FiniteDuration](secondsToFiniteDurationReads) and
    (__ \ "mean").read[FiniteDuration](secondsToFiniteDurationReads) and
    (__ \ "median").read[FiniteDuration](secondsToFiniteDurationReads)
  ) (TravelTime.apply _)

  implicit val timeFilterZonePropertiesReads: Reads[ZoneSearchProperties] = (
    (__ \ "travel_time_reachable").readNullable[TravelTime] and
    (__ \ "travel_time_all").readNullable[TravelTime] and
    (__ \ "coverage").readNullable[Double]
  ) (ZoneSearchProperties.apply _)

  implicit val timeFilterZoneReads: Reads[Zone] = (
    (__ \ "code").read[String] and
    (__ \ "properties").read[ZoneSearchProperties]
  ) (Zone.apply _)
}
