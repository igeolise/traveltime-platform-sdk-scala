package com.traveltime.sdk.dto.responses.timefilter

import com.traveltime.sdk.TravelTimeSDK.TravelTimeResponse
import com.traveltime.sdk.dto.common.Zone
import com.traveltime.sdk.dto.responses.timefilter.TimeFilterDistrictsResponse.SingleSearchResult
import play.api.libs.json.JsValue

case class TimeFilterDistrictsResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimeResponse

object TimeFilterDistrictsResponse {
  case class SingleSearchResult(
    id: String,
    districts: Seq[Zone]
  )
}
