package com.igeolise.traveltimesdk.dto.responses

import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeMapProps.TimeMapResponseProperties
import com.igeolise.traveltimesdk.dto.responses.TimeMapWktResponse.SingleSearchResult
import play.api.libs.json.JsValue

case class TimeMapWktResponse(results: Seq[SingleSearchResult], raw: JsValue)

object TimeMapWktResponse {
  sealed case class SingleSearchResult(
    searchId: String,
    shape: String,
    properties: TimeMapResponseProperties
  )

  sealed case class Shape(
    shell: Seq[Coords],
    holes: Seq[Seq[Coords]]
  )
}
