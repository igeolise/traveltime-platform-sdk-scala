package com.igeolise.traveltimesdk.dto.common

import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeFilterZonesProperty
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation

case class Zone(
  code: String,
  properties: ZoneSearchProperties
)

case class ZoneSearchProperties(
  travelTimeReachable: Option[TravelTime],
  travelTimeAll: Option[TravelTime],
  coverage: Option[Double]
)

case class TravelTime(
  min: Int,
  max: Int,
  mean: Int,
  median: Int
)

object ZoneSearches{
  case class DepartureSearch(
    id: String,
    coords: Coords,
    transportation: Transportation,
    departureTime: String,
    travelTime: Int,
    reachablePostcodesThreshold: Double,
    properties: Seq[TimeFilterZonesProperty],
    range: Option[FullRangeParams] = None
  )

  case class ArrivalSearch(
    id: String,
    coords: Coords,
    transportation: Transportation,
    arrivalTime: String,
    travelTime: Int,
    reachablePostcodesThreshold: Double,
    properties: Seq[TimeFilterZonesProperty],
    range: Option[FullRangeParams] = None
  )
}
