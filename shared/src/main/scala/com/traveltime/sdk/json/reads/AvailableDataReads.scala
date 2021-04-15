package com.traveltime.sdk.json.reads

import java.time.ZonedDateTime
import com.traveltime.sdk.dto.responses.{MapInfoResponse, SupportedLocationsResponse}
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsValue, Reads, __}
import CommonReads._

object AvailableDataReads {

  implicit val supportedLocationsLocationReads: Reads[SupportedLocationsResponse.Location] = (
    (__ \ "id").read[String] and
    (__ \ "map_name").read[String]
  )(SupportedLocationsResponse.Location.apply _)

  implicit val supportedLocationsResultReads: Reads[SupportedLocationsResponse] = (
    (__ \ "locations").read[Seq[SupportedLocationsResponse.Location]] and
    (__ \ "unsupported_locations").read[Seq[String]] and
    __.read[JsValue]
  )(SupportedLocationsResponse.apply _)

  implicit val mapInfoPTReads: Reads[MapInfoResponse.PublicTransportData] = (
    (__ \ "date_start").read[ZonedDateTime] and
    (__ \ "date_end").read[ZonedDateTime]
  )(MapInfoResponse.PublicTransportData.apply _)

  implicit val mapInfoFeaturesReads: Reads[MapInfoResponse.Features] = (
    (__ \ "public_transport").readNullable[MapInfoResponse.PublicTransportData] and
    (__ \ "fares").read[Boolean] and
    (__ \ "postcodes").read[Boolean]
  )(MapInfoResponse.Features.apply _)

  implicit val mapInfoMapReads: Reads[MapInfoResponse.Map] = (
    (__ \ "name").read[String] and
    (__ \ "features").read[MapInfoResponse.Features]
  )(MapInfoResponse.Map.apply _)

  implicit val mapInfoResultReads: Reads[MapInfoResponse] = (
    (__ \ "maps").read[Vector[MapInfoResponse.Map]] and
    __.read[JsValue]
  )(MapInfoResponse.apply _)
}
