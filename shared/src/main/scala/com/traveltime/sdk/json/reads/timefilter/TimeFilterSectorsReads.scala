package com.traveltime.sdk.json.reads.timefilter

import com.traveltime.sdk.dto.common.Zone
import com.traveltime.sdk.dto.responses.timefilter.TimeFilterSectorsResponse
import com.traveltime.sdk.dto.responses.timefilter.TimeFilterSectorsResponse.SingleSearchResult
import com.traveltime.sdk.json.reads.CommonReads._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsValue, Reads, __}

object TimeFilterSectorsReads  {

  implicit val timeFilterSectorsSingleReads: Reads[SingleSearchResult] = (
    (__ \ "search_id").read[String] and
    (__ \ "sectors").read[Seq[Zone]]
  ) (SingleSearchResult.apply _)

  implicit val timeFilterSectorsResultReads: Reads[TimeFilterSectorsResponse] = (
    (__ \ "results").read[Seq[TimeFilterSectorsResponse.SingleSearchResult]] and
    __.read[JsValue]
  )(TimeFilterSectorsResponse.apply _)
}
