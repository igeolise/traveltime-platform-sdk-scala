package com.igeolise.traveltimesdk.dto.responses.timefilter

import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterPostcodesResponse.SingleSearchResult
import play.api.libs.json.JsValue

case class TimeFilterPostcodesResponse(results: Seq[SingleSearchResult], raw: JsValue)

object TimeFilterPostcodesResponse {

  sealed case class SingleSearchResult(
    id: String,
    postCodes: Seq[Postcode]
  )

  sealed case class Postcode(
    code: String,
    properties: Seq[PostcodesProperties]
  )

  sealed case class PostcodesProperties(
    travelTime: Option[Int],
    distance: Option[Int]
  )

}
