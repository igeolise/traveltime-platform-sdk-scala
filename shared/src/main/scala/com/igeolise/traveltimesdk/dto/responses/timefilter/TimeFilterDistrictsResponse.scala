package com.igeolise.traveltimesdk.dto.responses.timefilter

import com.igeolise.traveltimesdk.dto.common.Zone
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterDistrictsResponse.SingleSearchResult
import play.api.libs.json.JsValue

case class TimeFilterDistrictsResponse(results: Seq[SingleSearchResult], raw: JsValue)

object TimeFilterDistrictsResponse {
  sealed case class SingleSearchResult(
    id: String,
    districts: Seq[Zone]
  )
}
