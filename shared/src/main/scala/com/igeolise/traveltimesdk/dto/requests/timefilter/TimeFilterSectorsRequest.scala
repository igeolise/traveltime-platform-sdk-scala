package com.igeolise.traveltimesdk.dto.requests.timefilter

import cats.Monad
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterSectorsReads._
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterSectorsWrites._
import com.igeolise.traveltimesdk.dto.common.ZoneSearches.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.requests.RequestUtils
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterSectorsResponse
import com.softwaremill.sttp.{HeaderNames, MediaTypes, Request, Uri}
import play.api.libs.json.Json

case class TimeFilterSectorsRequest(
  departureSearch: Seq[DepartureSearch],
  arrivalSearch: Seq[ArrivalSearch]
) extends TravelTimePlatformRequest[TimeFilterSectorsResponse] {

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, TimeFilterSectorsResponse]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[TimeFilterSectorsResponse]
    )

  override def sttpRequest(host: Uri): Request[String, Nothing] =
    RequestUtils.makePostRequest(
      Json.toJson(this),
      "v4/time-filter/postcode-sectors",
      host
    ).headers(HeaderNames.Accept -> MediaTypes.Json)
}