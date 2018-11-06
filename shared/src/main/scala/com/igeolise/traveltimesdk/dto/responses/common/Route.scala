package com.igeolise.traveltimesdk.dto.responses.common

import com.igeolise.traveltimesdk.dto.common.Coords
import scala.concurrent.duration.FiniteDuration

case class Route(
  departureTime: String,
  arrivalTime: String,
  parts: Seq[Route.RoutePart]
)

object Route {
  sealed trait RoutePart {
    val id: Int
    val `type`: String
    val mode: String
    val directions: String
    val distanceMeters: Int
    val travelTime: FiniteDuration
    val coords: Seq[Coords]
  }

  object RoutePart {
    case class BasicRoutePart(
      id: Int,
      `type`: String,
      mode: String,
      directions: String,
      distanceMeters: Int,
      travelTime: FiniteDuration,
      coords: Seq[Coords]
    ) extends RoutePart

    case class StartEndRoutePart(
      id: Int,
      `type`: String,
      mode: String,
      directions: String,
      distanceMeters: Int,
      travelTime: FiniteDuration,
      coords: Seq[Coords],
      direction: String
    ) extends RoutePart

    case class RoadRoutePart(
      id: Int,
      `type`: String,
      mode: String,
      directions: String,
      distanceMeters: Int,
      travelTime: FiniteDuration,
      coords: Seq[Coords],
      road: Option[String],
      turn: Option[String]
    ) extends RoutePart

    case class PublicTransportRoutePart(
      id: Int,
      `type`: String,
      mode: String,
      directions: String,
      distanceMeters: Int,
      travelTime: FiniteDuration,
      coords: Seq[Coords],
      line: String,
      departureStation: String,
      arrivalStation: String,
      departsAt: String,
      arrivesAt: String,
      numStops: Int
    ) extends RoutePart
  }
}
