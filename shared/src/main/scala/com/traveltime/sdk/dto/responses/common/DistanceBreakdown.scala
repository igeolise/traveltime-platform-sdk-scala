package com.traveltime.sdk.dto.responses.common

import com.traveltime.sdk.dto.responses.common.DistanceBreakdown.BreakdownPart

case class DistanceBreakdown(parts: Seq[BreakdownPart])

object DistanceBreakdown {

  case class BreakdownPart(
    mode: String,
    distanceMeters: Int
  )
}
