package com.igeolise.traveltimesdk.dto.requests.common

import enumeratum.{Enum, EnumEntry}

import scala.collection.immutable

object CommonProperties {
  sealed trait PropertyType extends EnumEntry { val propertyType: String }
  sealed trait TimeFilterZonesProperty extends PropertyType
  sealed trait RoutesRequestProperty extends PropertyType
  sealed trait TimeFilterFastProperty extends PropertyType
  sealed trait TimeFilterPostcodesProperty extends PropertyType
  sealed trait TimeMapRequestProperty extends PropertyType
  sealed trait TimeFilterRequestProperty extends PropertyType

  object PropertyType extends Enum[PropertyType] {
    val values: immutable.IndexedSeq[PropertyType] = findValues

    case object travelTime extends TimeFilterRequestProperty
      with TimeFilterPostcodesProperty with RoutesRequestProperty with TimeFilterFastProperty {
      override val propertyType: String = "travel_time"
    }

    case object distance extends TimeFilterRequestProperty with TimeFilterPostcodesProperty with RoutesRequestProperty{
      override val propertyType: String = "distance"
    }

    case object travelTimeReachable extends TimeFilterZonesProperty  {
      override val propertyType = "travel_time_reachable"
    }

    case object travelTimeAll extends TimeFilterZonesProperty {
      override val propertyType = "travel_time_all"
    }

    case object coverage extends TimeFilterZonesProperty  {
      override val propertyType = "coverage"
    }

    case object fares extends TimeFilterRequestProperty with RoutesRequestProperty with TimeFilterFastProperty{
      override val propertyType: String = "fares"
    }

    case object route extends TimeFilterRequestProperty with RoutesRequestProperty {
      override val propertyType: String = "route"
    }

    case object distanceBreakdown extends TimeFilterRequestProperty {
      override val propertyType: String = "distance_breakdown"
    }

    case object IsOnlyWalking extends TimeMapRequestProperty {
      override val propertyType: String = "is_only_walking"
    }
  }

  object TimeMapProps{
    case class TimeMapResponseProperties(
      isOnlyWalkingProperty: Option[Boolean]
    )
  }
}
