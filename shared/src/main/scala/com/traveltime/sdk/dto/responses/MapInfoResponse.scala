package com.traveltime.sdk.dto.responses

import java.time.ZonedDateTime

import com.traveltime.sdk.TravelTimeSDK.TravelTimeResponse
import com.traveltime.sdk.dto.responses.MapInfoResponse.Map
import play.api.libs.json.JsValue

case class MapInfoResponse(maps: Seq[Map], raw: JsValue) extends TravelTimeResponse

object MapInfoResponse {
  case class Map(
    name: String,
    features: Features
  )

  case class Features(
    publicTransportData: Option[PublicTransportData],
    fares: Boolean,
    postcodes: Boolean
  )

  case class PublicTransportData(
    dateStart: ZonedDateTime,
    dateEnd: ZonedDateTime
  )
}