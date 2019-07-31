package com.igeolise.traveltimesdk.dto.requests.common

import scala.concurrent.duration.FiniteDuration

object RangeParams {
  case class FullRangeParams(enabled: Boolean, maxResults: Int, width: FiniteDuration)
  case class RangeParams(enabled: Boolean, width: FiniteDuration)
}
