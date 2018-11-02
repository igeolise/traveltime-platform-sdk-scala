package com.igeolise.traveltimesdk.dto.responses.common

import com.igeolise.traveltimesdk.dto.responses.MapInfoResponse

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
  continent: Option[String],
  postcode: Option[String],

  ttpFeatures: Option[MapInfoResponse.Features]
)
