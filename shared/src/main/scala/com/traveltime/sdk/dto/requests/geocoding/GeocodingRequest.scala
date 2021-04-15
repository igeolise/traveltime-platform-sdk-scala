package com.traveltime.sdk.dto.requests.geocoding

import com.traveltime.sdk.TravelTimeSDK.{ResponseBody, TravelTimeRequest}
import com.traveltime.sdk.dto.common.{BCP47, Coords}
import com.traveltime.sdk.dto.requests.geocoding.QueryFragmentsUtils.queryFragments
import com.traveltime.sdk.dto.responses.GeocodingResponse
import com.traveltime.sdk.{TravelTimeHost, TravelTimeSDK}
import com.traveltime.sdk.{TravelTimeHost, TravelTimeSDK}
import com.traveltime.sdk.TravelTimeSDK.{ResponseBody, TravelTimeRequest}
import com.traveltime.sdk.dto.common.{BCP47, Coords}
import com.traveltime.sdk.dto.responses.GeocodingResponse
import sttp.client.Request

/**
  * Match a query string to geographic coordinates.
  *
  * @param query       A query to geocode. Can be an address, a postcode or a venue. For example SW1A 0AA or
  *                    Victoria street, London. Providing a country or city the request will get you more
  *                    accurate results.
  * @param focusCoords Latitude and longitude of a focus point. This will prioritize results around this point.
  *                    Note that this does not exclude results that are far away from the focus point.
  * @param countryCode Country code in ISO 3166-1 alpha-2 or alpha-3
  *                    [[https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes Country codes]].
  *                    Only return the results that are within the specified country.
  * @param acceptLanguage BCP47 tag https://tools.ietf.org/html/bcp47 . For example: "en", "fr-FR"
  */
case class GeocodingRequest(
  query: String,
  focusCoords: Option[Coords] = None,
  countryCode: Option[String] = None,
  acceptLanguage: Option[BCP47] = None
) extends TravelTimeRequest[GeocodingResponse] with GeocodingRequestWithLanguage {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK.createGetRequest(
      host
        .uri
        .path("v4", "geocoding", "search")
        .params(queryFragments(query, focusCoords, countryCode): _*)
    )
}
