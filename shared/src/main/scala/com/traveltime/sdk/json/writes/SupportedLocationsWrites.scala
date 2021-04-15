package com.traveltime.sdk.json.writes

import com.traveltime.sdk.json.writes.CommonWrites._
import com.traveltime.sdk.dto.requests.SupportedLocationsRequest
import play.api.libs.json.{JsValue, Json, Writes}

object SupportedLocationsWrites {
  implicit val supportedLocationsWrites: Writes[SupportedLocationsRequest] = new Writes[SupportedLocationsRequest] {
    override def writes(request: SupportedLocationsRequest): JsValue = Json.obj("locations" -> request.locations)
  }
}
