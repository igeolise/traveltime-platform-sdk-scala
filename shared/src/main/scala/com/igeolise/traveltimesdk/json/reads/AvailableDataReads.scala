package com.igeolise.traveltimesdk.json.reads

import java.time.ZonedDateTime
import com.igeolise.traveltimesdk.dto.responses.{MapInfoResponse, SupportedLocationsResponse}
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsValue, Reads, __}
import CommonReads._

object AvailableDataReads {
  implicit val supportedLocationsLocationReadsV4: Reads[SupportedLocationsResponse.Location] = (
    (__ \ "id").read[String] and
    (__ \ "map_name").read[String]
  )(SupportedLocationsResponse.Location.apply _)

  implicit val SupportedLocationsResultReadsV4: Reads[SupportedLocationsResponse] = (
    (__ \ "locations").read[Seq[SupportedLocationsResponse.Location]] and
    (__ \ "unsupported_locations").read[Seq[String]] and
    __.read[JsValue]
  )(SupportedLocationsResponse.apply _)

  implicit val mapInfoPTReadsV4: Reads[MapInfoResponse.PublicTransportData] = (
    (__ \ "date_start").read[ZonedDateTime] and
    (__ \ "date_end").read[ZonedDateTime]
  )(MapInfoResponse.PublicTransportData.apply _)

  implicit val mapInfoFeaturesReadsV4: Reads[MapInfoResponse.Features] = (
    (__ \ "public_transport").readNullable[MapInfoResponse.PublicTransportData] and
    (__ \ "fares").read[Boolean] and
    (__ \ "postcodes").read[Boolean]
  )(MapInfoResponse.Features.apply _)

  implicit val mapInfoMapReadsV4: Reads[MapInfoResponse.Map] = (
    (__ \ "name").read[String] and
    (__ \ "features").read[MapInfoResponse.Features]
  )(MapInfoResponse.Map.apply _)

  implicit val MapInfoResultReadsV4: Reads[MapInfoResponse] = (
    (__ \ "maps").read[Vector[MapInfoResponse.Map]] and
    __.read[JsValue]
  )(MapInfoResponse.apply _)
}
