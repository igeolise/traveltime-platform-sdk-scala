package com.igeolise.traveltimesdk.dto.responses

import com.igeolise.traveltimesdk.dto.common.BCP47
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformResponse

/**
 * @param contentLanguage BCP47 tag https://tools.ietf.org/html/bcp47 . For example: "en", "fr-FR"
 */
case class GeocodingLanguageResponse(
  contentLanguage: BCP47
)

case class GeocodingResponse(
  language: GeocodingLanguageResponse,
  properties: GeoJsonResponse[GeocodingResponseProperties]
) extends TravelTimePlatformResponse

case class GeocodingResponseProperties (
  name: String,
  label: String,

  score: Option[Double],
  houseNumber: Option[String],
  street: Option[String],
  region: Option[String],
  regionCode: Option[String],
  neighbourhood: Option[String],
  county: Option[String],
  macroRegion: Option[String],
  city: Option[String],
  country: Option[String],
  countryCode: Option[String],
  localAdmin: Option[String],
  continent: Option[String],
  postcode: Option[String],

  ttpFeatures: Option[MapInfoResponse.Features]
)
