package com.igeolise.traveltimesdk.json.reads.timefilter

import com.igeolise.traveltimesdk.dto.common.Zone
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterDistrictsResponse
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterDistrictsResponse.SingleSearchResult
import com.igeolise.traveltimesdk.json.reads.CommonReads._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsValue, Reads, __}

object TimeFilterDistrictsReads {

  implicit val timeFilterDistrictsSingleReads: Reads[SingleSearchResult] = (
    (__ \ "search_id").read[String] and
    (__ \ "districts").read[Seq[Zone]]
  ) (SingleSearchResult.apply _)

  implicit val timeFilterDistrictsSuccessReads: Reads[TimeFilterDistrictsResponse] = (
    (__ \ "results").read[Seq[SingleSearchResult]] and
    __.read[JsValue]
  )(TimeFilterDistrictsResponse.apply _)
}
