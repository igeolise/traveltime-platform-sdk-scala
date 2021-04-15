package com.traveltime.sdk.dto.responses

import com.traveltime.sdk.TravelTimeSDK.TravelTimeResponse
import com.traveltime.sdk.dto.common.Coords
import com.traveltime.sdk.dto.requests.common.TimeMapProps.TimeMapResponseProperties
import com.traveltime.sdk.dto.responses.TimeMapWktResponse.SingleSearchResult
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
