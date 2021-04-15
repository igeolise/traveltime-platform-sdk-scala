package com.traveltime.sdk.dto.requests.geocoding

import com.traveltime.sdk.TravelTimeSDK
import com.traveltime.sdk.TravelTimeSDK.TransformFn
import com.traveltime.sdk.dto.common.BCP47
import com.traveltime.sdk.dto.responses.{GeoJsonResponse, GeocodingResponse, GeocodingResponseProperties}
import com.traveltime.sdk.json.reads.GeocodingReads._

trait GeocodingRequestWithLanguage {
  val acceptLanguage: Option[BCP47]

  final val transform: TransformFn[GeocodingResponse] =
    TravelTimeSDK.addLanguageToResponse(_.validate[GeoJsonResponse[GeocodingResponseProperties]])
}