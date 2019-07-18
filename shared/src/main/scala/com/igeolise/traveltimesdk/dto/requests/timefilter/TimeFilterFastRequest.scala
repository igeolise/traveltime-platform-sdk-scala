package com.igeolise.traveltimesdk.dto.requests.timefilter

import cats.Monad
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterFastReads._
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterFastWrites._
import com.igeolise.traveltimesdk.dto.requests.RequestUtils
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformRequest
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterFastRequest.ArrivalSearch
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeFilterFastProperty
import com.igeolise.traveltimesdk.dto.requests.common.Location
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.TimeFilterFastTransportation
import com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterFastResponse
import com.softwaremill.sttp._
import play.api.libs.json.Json

import scala.concurrent.duration.FiniteDuration

case class TimeFilterFastRequest(
  locations: Seq[Location],
  arrivalSearches: ArrivalSearch
) extends TravelTimePlatformRequest[TimeFilterFastResponse] {

  override def send[R[_] : Monad, S](
    sttpRequest: RequestUtils.SttpRequest[R, S]
  ): R[Either[TravelTimeSdkError, TimeFilterFastResponse]] =
    RequestUtils.send(
      sttpRequest,
      _.validate[TimeFilterFastResponse]
    )


  override def sttpRequest(host: Uri): Request[String, Nothing] =
    RequestUtils.makePostRequest(
      Json.toJson(this),
      "v4/time-filter/fast",
      host
    ).headers(HeaderNames.Accept -> MediaTypes.Json)
}

object TimeFilterFastRequest {
  sealed trait SearchType

  sealed trait ArrivalTimePeriod {
    val periodType: String
  }
  case object WeekdayMorning extends ArrivalTimePeriod {
    override val periodType: String = "weekday_morning"
  }

  case class ArrivalSearch(
    manyToOne: Seq[ManyToOne],
    oneToMany: Seq[OneToMany]
  )

  sealed case class ManyToOne(
    id: String,
    arrivalLocationId: String,
    departureLocationIds: Seq[String],
    transportation: TimeFilterFastTransportation,
    travelTime: FiniteDuration,
    arrivalTimePeriod: ArrivalTimePeriod,
    properties: Seq[TimeFilterFastProperty]
  ) extends SearchType

  sealed case class OneToMany(
    id: String,
    departureLocationId: String,
    arrivalLocationIds: Seq[String],
    transportation: TimeFilterFastTransportation,
    travelTime: FiniteDuration,
    arrivalTimePeriod: ArrivalTimePeriod,
    properties: Seq[TimeFilterFastProperty]
  ) extends SearchType
}
