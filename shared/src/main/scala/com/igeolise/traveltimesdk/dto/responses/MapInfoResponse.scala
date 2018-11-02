package com.igeolise.traveltimesdk.dto.responses

import com.igeolise.traveltimesdk.dto.responses.MapInfoResponse.Map
import play.api.libs.json.JsValue

case class MapInfoResponse(maps: Seq[Map], raw: JsValue)

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
    dateStart: String,
    dateEnd: String
  )

}