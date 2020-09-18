package com.igeolise.traveltimesdk.dto.requests.geocoding

import com.igeolise.traveltimesdk.TravelTimeSDK
import com.igeolise.traveltimesdk.TravelTimeSDK.TransformFn
import com.igeolise.traveltimesdk.dto.common.BCP47
import com.igeolise.traveltimesdk.dto.responses.{GeoJsonResponse, GeocodingResponse, GeocodingResponseProperties}
import com.igeolise.traveltimesdk.json.reads.GeocodingReads._

trait GeocodingRequestWithLanguage {
  val acceptLanguage: Option[BCP47]

  final val transform: TransformFn[GeocodingResponse] =
    TravelTimeSDK.addLanguageToResponse(_.validate[GeoJsonResponse[GeocodingResponseProperties]])
}