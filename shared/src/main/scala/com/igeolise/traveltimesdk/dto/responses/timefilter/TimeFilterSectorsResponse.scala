package com.igeolise.traveltimesdk.dto.responses.timefilter

import com.igeolise.traveltimesdk.TravelTimeSDK.TravelTimeResponse
import com.igeolise.traveltimesdk.dto.common.Zone
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterSectorsResponse.SingleSearchResult
import play.api.libs.json.JsValue

case class TimeFilterSectorsResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimeResponse

object TimeFilterSectorsResponse {
  case class SingleSearchResult(
    id: String,
    sectors: Seq[Zone]
  )
}
