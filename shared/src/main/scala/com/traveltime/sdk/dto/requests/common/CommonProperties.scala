package com.traveltime.sdk.dto.requests.common

object CommonProperties {
  sealed trait Property { val propertyType: String }
  sealed trait RoutesRequestProperty        extends Property
  sealed trait TimeFilterFastProperty       extends Property
  sealed trait TimeMapRequestProperty       extends Property
  sealed trait TimeFilterZonesProperty      extends Property
  sealed trait TimeFilterRequestProperty    extends Property
  sealed trait TimeFilterPostcodesProperty  extends Property

  case object TravelTime extends TimeFilterRequestProperty
    with TimeFilterPostcodesProperty with RoutesRequestProperty with TimeFilterFastProperty {
    override val propertyType: String = "travel_time"
  }

  case object Distance extends TimeFilterRequestProperty with TimeFilterPostcodesProperty with RoutesRequestProperty {
    override val propertyType: String = "distance"
  }

  case object TravelTimeReachable extends TimeFilterZonesProperty {
    override val propertyType = "travel_time_reachable"
  }

  case object TravelTimeAll extends TimeFilterZonesProperty {
    override val propertyType = "travel_time_all"
  }

  case object Coverage extends TimeFilterZonesProperty {
    override val propertyType = "coverage"
  }

  case object Fares extends TimeFilterRequestProperty with RoutesRequestProperty with TimeFilterFastProperty {
    override val propertyType: String = "fares"
  }

  case object Route extends TimeFilterRequestProperty with RoutesRequestProperty {
    override val propertyType: String = "route"
  }

  case object DistanceBreakdown extends TimeFilterRequestProperty {
    override val propertyType: String = "distance_breakdown"
  }

  case object IsOnlyWalking extends TimeMapRequestProperty {
    override val propertyType: String = "is_only_walking"
  }

  /**
   * Usage of this experimental property is permitted only for TravelTime Platform developers.
   */
  case object Agencies extends TimeMapRequestProperty {
    override val propertyType: String = "agencies"
  }
  }

  object TimeMapProps {
    case class Agency(name: String, modes: Vector[String])

    case class TimeMapResponseProperties(
      isOnlyWalkingProperty: Option[Boolean],
      agencies: Option[Vector[Agency]]
    )
}
