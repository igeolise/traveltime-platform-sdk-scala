package com.traveltime.sdk.dto.responses

import com.traveltime.sdk.TravelTimeSDK.TravelTimeResponse
import com.traveltime.sdk.dto.responses.SupportedLocationsResponse.Location
import play.api.libs.json.JsValue

case class SupportedLocationsResponse(
  locations: Seq[Location],
  unsupportedLocations: Seq[String],
  raw: JsValue
) extends TravelTimeResponse

object SupportedLocationsResponse {
  case class Location(
    id: String,
    mapName: String
  )
}
