package com.igeolise.traveltimesdk.dto.requests.geocoding

import com.igeolise.traveltimesdk.TravelTimeSDK.{ResponseBody, TravelTimeRequest}
import com.igeolise.traveltimesdk.dto.common.{BCP47, Coords}
import com.igeolise.traveltimesdk.dto.responses.GeocodingResponse
import com.igeolise.traveltimesdk.{TravelTimeHost, TravelTimeSDK}
import sttp.client.Request

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
) extends TravelTimeRequest[GeocodingResponse] with GeocodingRequestWithLanguage {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] = {
    val params =
      Seq(
        ("lat", coordinates.lat.toString),
        ("lng", coordinates.lng.toString),
      ) ++ withinCountry.map(("within.country", _))

    TravelTimeSDK.createGetRequest(
      host
        .uri
        .path("v4", "geocoding", "reverse")
        .params(params: _*)
    )
  }
}