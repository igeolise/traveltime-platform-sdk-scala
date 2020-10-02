package com.igeolise.traveltimesdk.dto.responses

import com.igeolise.traveltimesdk.TravelTimeSDK.TravelTimeResponse
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.common.TimeMapProps.TimeMapResponseProperties
import com.igeolise.traveltimesdk.dto.responses.TimeMapWktResponse.SingleSearchResult
import play.api.libs.json.JsValue

case class TimeMapWktResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimeResponse

object TimeMapWktResponse {
  case class SingleSearchResult(
    searchId: String,
    shape: String,
    properties: TimeMapResponseProperties
  )

  case class Shape(
    shell: Seq[Coords],
    holes: Seq[Seq[Coords]]
  )
}
