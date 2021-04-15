package com.traveltime.sdk.dto.responses

import com.traveltime.sdk.TravelTimeSDK.TravelTimeResponse
import com.traveltime.sdk.dto.requests.common.TimeMapProps.TimeMapResponseProperties
import com.traveltime.sdk.dto.responses.TimeMapBoxesResponse.SingleSearchResult
import play.api.libs.json.JsValue

case class TimeMapBoxesResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimeResponse

object TimeMapBoxesResponse {
  case class SingleSearchResult(
    searchId: String,
    boundingBoxes: Seq[BoundingBox],
    properties: TimeMapResponseProperties
  )

  case class BoundingBox(
    envelope: Container,
    boxes: Seq[Container]
  )

  case class Container(
    minLat: Double,
    maxLat: Double,
    minLng: Double,
    maxLng: Double
  )
}
