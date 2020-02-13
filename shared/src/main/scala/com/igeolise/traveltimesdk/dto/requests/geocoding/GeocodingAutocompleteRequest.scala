package com.igeolise.traveltimesdk.dto.requests.geocoding

import com.igeolise.traveltimesdk.dto.common.{BCP47, Coords}
import com.igeolise.traveltimesdk.dto.requests.geocoding.QueryFragmentsUtils.queryFragments
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.responses.GeocodingResponse
import com.softwaremill.sttp.Uri

/**
  * Match a query string to geographic coordinates.
  * This endpoint is designed to be used in location input with type-ahead support.
  * The difference from Geocoding [[GeocodingRequest]] is that this endpoint uses a different query parser,
  * which takes into account that the user is typing and query may not be complete.
  *
  * @param query       A query to geocode. Set this parameter to whatever the user has typed in the type-ahead input.
  *                    Must be at least 2 characters long
  * @param focusCoords Latitude and longitude of a focus point. This will prioritize results around this point.
  *                    Note that this does not exclude results that are far away from the focus point.
  * @param countryCode Country code in ISO 3166-1 alpha-2 or alpha-3
  *                    [[https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes Country codes]].
  *                    Only return the results that are within the specified country.
  * @param acceptLanguage BCP47 tag https://tools.ietf.org/html/bcp47 . For example: "en", "fr-FR"
  */
case class GeocodingAutocompleteRequest(
  query: String,
  focusCoords: Option[Coords] = None,
  countryCode: Option[String] = None,
  acceptLanguage: Option[BCP47] = None
) extends TravelTimePlatformRequest[GeocodingResponse] with GeocodingRequestWithLanguage {
  val endpoint = GeocodingAutocompleteRequest.endpoint

  def queryUri(host: Uri): Uri =
    host
      .path(endpoint)
      .params(queryFragments(query, focusCoords, countryCode): _*)
}

object GeocodingAutocompleteRequest {
  val endpoint = s"v4/geocoding/${Autocomplete.endpoint}"
}