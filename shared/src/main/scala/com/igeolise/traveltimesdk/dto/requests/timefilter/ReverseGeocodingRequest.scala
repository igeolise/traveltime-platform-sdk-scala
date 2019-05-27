package com.igeolise.traveltimesdk.dto.requests.timefilter

import cats.Monad
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.RequestUtils
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.responses.{GeoJsonResponse, TravelTimeSdkError}
import com.igeolise.traveltimesdk.dto.responses.common.GeocodingResponseProperties
import com.softwaremill.sttp.{sttp, Request, Uri}
import com.igeolise.traveltimesdk.json.reads.GeocodingReads._
import scala.language.higherKinds

case class ReverseGeocodingRequest(
  coordinates: Coords,
  withinCountry: Option[String]
) extends TravelTimePlatformRequest[GeoJsonResponse[GeocodingResponseProperties]] {

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, GeoJsonResponse[GeocodingResponseProperties]]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[GeoJsonResponse[GeocodingResponseProperties]]
  )

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
