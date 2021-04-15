package com.traveltime.sdk.dto.responses.timefilter

import com.traveltime.sdk.TravelTimeSDK.TravelTimeResponse
import com.traveltime.sdk.dto.responses.timefilter.TimeFilterPostcodesResponse.SingleSearchResult
import play.api.libs.json.JsValue

import scala.concurrent.duration.FiniteDuration

case class TimeFilterPostcodesResponse(results: Seq[SingleSearchResult], raw: JsValue) extends TravelTimeResponse

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
