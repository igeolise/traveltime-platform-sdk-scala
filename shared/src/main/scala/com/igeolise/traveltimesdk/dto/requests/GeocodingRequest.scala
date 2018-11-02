package com.igeolise.traveltimesdk.dto.requests

import cats.Monad
import com.igeolise.traveltimesdk.json.reads.GeocodingReads._
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.responses.{GeoJsonResponse, TravelTimeSdkError}
import com.igeolise.traveltimesdk.dto.responses.common.GeocodingResponseProperties
import com.softwaremill.sttp.{Uri, _}

case class GeocodingRequest(
  query: String, focusCoords: Option[Coords] = None, countryCode: Option[String] = None
) extends TravelTimePlatformRequest[GeoJsonResponse[GeocodingResponseProperties]]  {

  override def send[R[_] : Monad, S](sttpRequest: RequestUtils.SttpRequest[R, S])
  : R[Either[TravelTimeSdkError, GeoJsonResponse[GeocodingResponseProperties]]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[GeoJsonResponse[GeocodingResponseProperties]]
    )

  override def sttpRequest(host: Uri): Request[String, Nothing] = {
    def request(
      query: String, host: Uri, focusCoords: Option[Coords], countryCode: Option[String]
    ): Request[String, Nothing] = {
      val lat = focusCoords.map(_.lat)
      val lng = focusCoords.map(_.lng)

      sttp
        .get(uri"$host/v4/geocoding/search?query=$query&focus.lat=$lat&focus.lng=$lng&within.country=$countryCode")
        .contentType(MediaTypes.Json)
    }
    request(query, host, focusCoords, countryCode)
  }
}
