package com.igeolise.traveltimesdk.json.writes

import com.igeolise.traveltimesdk.json.writes.CommonWrites._
import com.igeolise.traveltimesdk.dto.requests.SupportedLocationsRequest
import play.api.libs.json.{JsValue, Json, Writes}

object SupportedLocationsWrites {
  implicit val supportedLocationsWrites: Writes[SupportedLocationsRequest] = new Writes[SupportedLocationsRequest] {
    override def writes(request: SupportedLocationsRequest): JsValue = Json.obj("locations" -> request.locations)
  }
}
