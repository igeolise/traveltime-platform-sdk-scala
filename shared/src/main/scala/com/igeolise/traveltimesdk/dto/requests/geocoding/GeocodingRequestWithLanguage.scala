package com.igeolise.traveltimesdk.dto.requests.geocoding

import cats.Monad
import com.igeolise.traveltimesdk.dto.common.BCP47
import com.igeolise.traveltimesdk.dto.requests.RequestUtils
import com.igeolise.traveltimesdk.dto.responses.{GeoJsonResponse, GeocodingResponse, GeocodingResponseProperties, TravelTimeSdkError}
import com.igeolise.traveltimesdk.json.reads.GeocodingReads._
import com.softwaremill.sttp.{Request, Uri, _}

trait GeocodingRequestWithLanguage {
  abstract val acceptLanguage: Option[BCP47]

  abstract def queryUri(host: Uri): Uri

  final def send[R[_] : Monad, S](sttpRequest: RequestUtils.SttpRequest[R, S]): R[Either[TravelTimeSdkError, GeocodingResponse]] =
    RequestUtils.sendModified(
      sttpRequest,
      RequestUtils.addLanguageToResponse(
        _.validate[GeoJsonResponse[GeocodingResponseProperties]])
    )

  final def sttpRequest(host: Uri): Request[String, Nothing] =
    sttp.get(queryUri(host))
}