package com.igeolise.traveltimesdk.dto.requests.geocoding

import com.igeolise.traveltimesdk.dto.common.{BCP47, Coords}
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.responses.GeocodingResponse
import com.softwaremill.sttp._

/**
  * Attempt to match a latitude, longitude pair to an address.
  *
  * @param coordinates Coordinates to match.
  * @param withinCountry Country code in ISO 3166-1 alpha-2 or alpha-3
  *                      [[https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes Country codes]].
  *                      Useful when performing reverse geocoding requests near a border.
  */
case class ReverseGeocodingRequest(
    coordinates: Coords,
    withinCountry: Option[String] = None,
    acceptLanguage: Option[BCP47] = None
) extends TravelTimePlatformRequest[GeocodingResponse] with GeocodingRequestWithLanguage {
  val endpoint = s"v4/geocoding/${Reverse.endpoint}"

  def queryUri(host: Uri): Uri = {
    val lat = coordinates.lat
    val lng = coordinates.lng
    uri"$host/$endpoint?lat=$lat&lng=$lng&within.country=$withinCountry"
  }
}
