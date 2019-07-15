package com.igeolise.traveltimesdk.dto.responses

import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformResponse
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeMapProps.TimeMapResponseProperties
import com.igeolise.traveltimesdk.dto.responses.TimeMapResponse.SingleSearchResult
import play.api.libs.json.JsValue

case class TimeMapResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimePlatformResponse

object TimeMapResponse {
  case class SingleSearchResult(
    searchId: String,
    shapes: Seq[Shape],
    properties: TimeMapResponseProperties
  )

  case class Shape(
    shell: Seq[Coords],
    holes: Seq[Seq[Coords]]
  )
}
