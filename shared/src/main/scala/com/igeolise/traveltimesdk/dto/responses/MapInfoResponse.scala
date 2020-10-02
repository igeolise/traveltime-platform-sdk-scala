package com.igeolise.traveltimesdk.dto.responses

import java.time.ZonedDateTime

import com.igeolise.traveltimesdk.TravelTimeSDK.TravelTimeResponse
import com.igeolise.traveltimesdk.dto.responses.MapInfoResponse.Map
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