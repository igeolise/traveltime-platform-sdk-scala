package com.igeolise.traveltimesdk.dto.requests.geocoding

import cats.Monad
import com.igeolise.traveltimesdk.dto.common.{BCP47, Coords}
import com.igeolise.traveltimesdk.dto.requests.RequestUtils
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.responses.{GeoJsonResponse, GeocodingResponse, GeocodingResponseProperties, TravelTimeSdkError}
import com.softwaremill.sttp.{Request, _}
import com.igeolise.traveltimesdk.json.reads.GeocodingReads._

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
                                    withinCountry: Option[String],
                                    acceptLanguage: Option[BCP47]
) extends TravelTimePlatformRequest[GeocodingResponse] with GeocodingRequestWithLanguage {

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, GeocodingResponse]] = {
    RequestUtils.sendGeocoding(
      sttpRequest,
      _.validate[GeoJsonResponse[GeocodingResponseProperties]]
    )
  }

  override def sttpRequest(host: Uri): Request[String, Nothing] = {
    val endpoint = "v4/geocoding/reverse"
    val parameters: Map[String, String] =
      Vector(
        Some("lat" -> coordinates.lat.toString),
        Some("lng" -> coordinates.lng.toString),
        withinCountry.map(countryCode => "within.country" -> countryCode)
      ).flatten.toMap

    val uri = Uri("https", host.host).path(endpoint.split("/")).params(parameters)

    sttp.get(uri)
  }
}
