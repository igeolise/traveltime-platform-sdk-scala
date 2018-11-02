package com.igeolise.traveltimesdk.dto.requests.common

object RangeParams {
  case class FullRangeParams(enabled: Boolean, max_results: Int, width: Int)
  case class RangeParams(enabled: Boolean, width: Int)
}
