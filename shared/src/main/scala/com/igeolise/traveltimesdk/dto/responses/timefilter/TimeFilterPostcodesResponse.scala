package com.igeolise.traveltimesdk.dto.responses.timefilter

import com.igeolise.traveltimesdk.dto.requests.RequestUtils.TravelTimePlatformResponse
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterPostcodesResponse.SingleSearchResult
import play.api.libs.json.JsValue

import scala.concurrent.duration.FiniteDuration

case class TimeFilterPostcodesResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimePlatformResponse

object TimeFilterPostcodesResponse {
  case class SingleSearchResult(
    id: String,
    postcodes: Seq[Postcode]
  )

  case class Postcode(
    code: String,
    properties: Seq[PostcodesProperties]
  )

  case class PostcodesProperties(
    travelTime: Option[FiniteDuration],
    distance: Option[Int]
  )
}
