package com.igeolise.traveltimesdk.dto.responses

import com.igeolise.traveltimesdk.TravelTimeSDK.TravelTimeResponse
import com.igeolise.traveltimesdk.dto.requests.common.TimeMapProps.TimeMapResponseProperties
import com.igeolise.traveltimesdk.dto.responses.TimeMapBoxesResponse.SingleSearchResult
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
