package com.igeolise.traveltimesdk.dto.requests.timefilter

import cats.Monad
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterDistrictsReads._
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterDistrictsWrites._
import com.igeolise.traveltimesdk.dto.common.ZoneSearches
import com.igeolise.traveltimesdk.dto.requests.RequestUtils
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterDistrictsResponse
import com.softwaremill.sttp.{HeaderNames, MediaTypes, Request, Uri}
import play.api.libs.json.Json

case class TimeFilterDistrictsRequest (
  departureSearch: Seq[ZoneSearches.DepartureSearch],
  arrivalSearch: Seq[ZoneSearches.ArrivalSearch]
) extends TravelTimePlatformRequest[TimeFilterDistrictsResponse]  {
  val endpoint = TimeFilterDistrictsRequest.endpoint

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, TimeFilterDistrictsResponse]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[TimeFilterDistrictsResponse]
    )

  override def sttpRequest(host: Uri): Request[String, Nothing] = {
    RequestUtils.makePostRequest(
      Json.toJson(this),
      endpoint,
      host
    ).headers(HeaderNames.Accept -> MediaTypes.Json)
  }
}

object TimeFilterDistrictsRequest {
  val endpoint = "v4/time-filter/postcode-districts"
}