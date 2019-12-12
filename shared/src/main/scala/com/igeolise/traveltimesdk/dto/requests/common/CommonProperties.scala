package com.igeolise.traveltimesdk.dto.requests.common

import enumeratum.{Enum, EnumEntry}

import scala.collection.immutable

object CommonProperties {
  sealed trait PropertyType extends EnumEntry { val propertyType: String }
  sealed trait RoutesRequestProperty        extends PropertyType
  sealed trait TimeFilterFastProperty       extends PropertyType
  sealed trait TimeMapRequestProperty       extends PropertyType
  sealed trait TimeFilterZonesProperty      extends PropertyType
  sealed trait TimeFilterRequestProperty    extends PropertyType
  sealed trait TimeFilterPostcodesProperty  extends PropertyType

  object PropertyType extends Enum[PropertyType] {
    val values: immutable.IndexedSeq[PropertyType] = findValues

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
}
