package com.igeolise.traveltimesdk.json.reads.timefilter

import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterFastResponse
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterFastResponse._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsValue, Reads, __}

object TimeFilterFastReads {

  implicit val timeFilterTicketReads: Reads[Ticket] = (
    (__ \ "type").read[String] and
    (__ \ "price").read[Double] and
    (__ \ "currency").read[String]
  ) (Ticket.apply _)

  implicit val timeFilterFaresReads: Reads[Fares] =
    (__ \ "tickets_total").read[Seq[Ticket]].map(Fares.apply)

  implicit val timeFilterPropertiesReads: Reads[Properties] = (
    (__ \ "travel_time").readNullable[Int] and
    (__ \ "fares").read[Fares]
  ) (Properties.apply _)

  implicit val timeFilterLocationReads: Reads[Location] = (
    (__ \ "id").read[String] and
    (__ \ "properties").read[Properties]
  ) (Location.apply _)

  implicit val timeFiltersSingleReads: Reads[SingleSearchResult] = (
    (__ \ "search_id").read[String] and
    (__ \ "locations").read[Seq[Location]] and
    (__ \ "unreachable").read[Seq[String]]
  ) (SingleSearchResult.apply _)

  implicit val timeFilterPostcodesSuccessReads: Reads[TimeFilterFastResponse] = (
    (__ \ "results").read[Seq[TimeFilterFastResponse.SingleSearchResult]] and
    __.read[JsValue]
  )(TimeFilterFastResponse.apply _)

}
