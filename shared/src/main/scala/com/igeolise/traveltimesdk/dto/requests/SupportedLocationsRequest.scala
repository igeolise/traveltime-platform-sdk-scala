package com.igeolise.traveltimesdk.dto.requests

import cats.Monad
import com.igeolise.traveltimesdk.json.reads.AvailableDataReads._
import com.igeolise.traveltimesdk.json.writes.SupportedLocationsWrites._
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.requests.common.Location
import com.igeolise.traveltimesdk.dto.responses.{SupportedLocationsResponse, TravelTimeSdkError}
import com.softwaremill.sttp.{HeaderNames, Id, MediaTypes, RequestT, Uri}
import play.api.libs.json.Json

case class SupportedLocationsRequest(
  locations: Seq[Location]
) extends TravelTimePlatformRequest[SupportedLocationsResponse] {

  override def send[R[_] : Monad, S](sttpRequest: RequestUtils.SttpRequest[R, S])
  : R[Either[TravelTimeSdkError, SupportedLocationsResponse]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[SupportedLocationsResponse]
    )

  override def sttpRequest(host: Uri): RequestT[Id, String, Nothing] = {
    RequestUtils.makePostRequest(
      Json.toJson(this),
      "v4/supported-locations",
      host
    ).headers(HeaderNames.Accept -> MediaTypes.Json)
  }
}
