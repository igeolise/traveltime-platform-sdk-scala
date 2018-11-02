package com.igeolise.traveltimesdk.dto.responses.timefilter

import com.igeolise.traveltimesdk.dto.common.Zone
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterSectorsResponse.SingleSearchResult
import play.api.libs.json.JsValue

case class TimeFilterSectorsResponse(results: Seq[SingleSearchResult], raw: JsValue)

object TimeFilterSectorsResponse {
  sealed case class SingleSearchResult(
    id: String,
    sectors: Seq[Zone]
  )
}
