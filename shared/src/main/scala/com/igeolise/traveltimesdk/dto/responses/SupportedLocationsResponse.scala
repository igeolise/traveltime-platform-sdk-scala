package com.igeolise.traveltimesdk.dto.responses

import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformResponse
import com.igeolise.traveltimesdk.dto.responses.SupportedLocationsResponse.Location
import play.api.libs.json.JsValue

case class SupportedLocationsResponse(
  locations: Seq[Location],
  unsupportedLocations: Seq[String],
  raw: JsValue
) extends TravelTimePlatformResponse

object SupportedLocationsResponse {
  case class Location(
    id: String,
    mapName: String
  )
}
