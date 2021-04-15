package com.traveltime.sdk.dto.responses.timefilter

import com.traveltime.sdk.TravelTimeSDK.TravelTimeResponse
import com.traveltime.sdk.dto.common.Zone
import com.traveltime.sdk.dto.responses.timefilter.TimeFilterSectorsResponse.SingleSearchResult
import com.traveltime.sdk.TravelTimeSDK.TravelTimeResponse
import play.api.libs.json.JsValue

case class TimeFilterSectorsResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimeResponse

object TimeFilterSectorsResponse {
  case class SingleSearchResult(
    id: String,
    sectors: Seq[Zone]
  )
}
