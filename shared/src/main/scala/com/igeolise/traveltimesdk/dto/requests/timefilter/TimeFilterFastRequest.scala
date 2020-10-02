package com.igeolise.traveltimesdk.dto.requests.timefilter

import com.igeolise.traveltimesdk.TravelTimeSDK.{ResponseBody, TransformFn, TravelTimeRequest}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeFilterFastProperty
import com.igeolise.traveltimesdk.dto.requests.common.Location
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.TimeFilterFastTransportation
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterFastRequest.ArrivalSearch
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterFastResponse
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterFastReads._
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterFastWrites._
import com.igeolise.traveltimesdk.{TravelTimeHost, TravelTimeSDK}
import play.api.libs.json.Json
import sttp.client.Request

import scala.concurrent.duration.FiniteDuration

case class TimeFilterFastRequest(
  locations: Seq[Location],
  arrivalSearches: ArrivalSearch
) extends TravelTimeRequest[TimeFilterFastResponse] {

  final def sttpRequest[S](host: TravelTimeHost): Request[ResponseBody, S] =
    TravelTimeSDK.createPostRequest(Json.toJson(this), host.uri.path("v4", "time-filter", "fast"))

  final val transform: TransformFn[TimeFilterFastResponse] =
    response => TravelTimeSDK.handleJsonResponse(response.body, _.validate[TimeFilterFastResponse])
}

object TimeFilterFastRequest {

  sealed abstract class ArrivalTimePeriod(val periodType: String)
  case object WeekdayMorning extends ArrivalTimePeriod("weekday_morning")

  case class ArrivalSearch(
    manyToOne: Seq[ManyToOne],
    oneToMany: Seq[OneToMany]
  )

  case class ManyToOne(
    id: String,
    arrivalLocationId: String,
    departureLocationIds: Seq[String],
    transportation: TimeFilterFastTransportation,
    travelTime: FiniteDuration,
    arrivalTimePeriod: ArrivalTimePeriod,
    properties: Seq[TimeFilterFastProperty]
  )

  case class OneToMany(
    id: String,
    departureLocationId: String,
    arrivalLocationIds: Seq[String],
    transportation: TimeFilterFastTransportation,
    travelTime: FiniteDuration,
    arrivalTimePeriod: ArrivalTimePeriod,
    properties: Seq[TimeFilterFastProperty]
  )
}
