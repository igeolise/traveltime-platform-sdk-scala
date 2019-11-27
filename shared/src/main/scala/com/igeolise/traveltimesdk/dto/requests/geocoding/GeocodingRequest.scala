package com.igeolise.traveltimesdk.dto.requests.geocoding

import cats.Monad
import com.igeolise.traveltimesdk.dto.common.{BCP47, Coords}
import com.igeolise.traveltimesdk.dto.requests.RequestUtils
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.responses.{GeoJsonResponse, GeocodingResponse, GeocodingResponseProperties, TravelTimeSdkError}
import com.igeolise.traveltimesdk.json.reads.GeocodingReads._
import com.softwaremill.sttp.{Uri, _}

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
) extends GeocodingRequestWithLanguage with TravelTimePlatformRequest[GeocodingResponse]  {

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, GeocodingResponse]] = {
    RequestUtils.sendModified(
      sttpRequest,
      RequestUtils.addLanguageToResponse(_.validate[GeoJsonResponse[GeocodingResponseProperties]])
    )
  }

  override def sttpRequest(host: Uri): Request[String, Nothing] = {
    def request(
      query: String, host: Uri, focusCoords: Option[Coords], countryCode: Option[String]
    ): Request[String, Nothing] = {

      sttp
        .get(queryUri(host))
        .contentType(MediaTypes.Json)
    }
    request(query, host, focusCoords, countryCode)
  }

  def queryUri(host: Uri, resourceType: String = "search"): Uri = {
    val lat = focusCoords.map(_.lat)
    val lng = focusCoords.map(_.lng)
    uri"$host/v4/geocoding/$resourceType?query=$query&focus.lat=$lat&focus.lng=$lng&within.country=$countryCode"
  }
}
