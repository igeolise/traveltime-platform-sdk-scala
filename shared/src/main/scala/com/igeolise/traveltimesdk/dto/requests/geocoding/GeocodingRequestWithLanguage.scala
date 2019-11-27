package com.igeolise.traveltimesdk.dto.requests.geocoding

import com.igeolise.traveltimesdk.dto.common.BCP47

trait GeocodingRequestWithLanguage {
  val acceptLanguage: Option[BCP47]
}