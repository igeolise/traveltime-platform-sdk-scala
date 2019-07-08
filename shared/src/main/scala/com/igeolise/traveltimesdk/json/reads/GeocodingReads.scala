package com.igeolise.traveltimesdk.json.reads

import com.igeolise.traveltimesdk.json.reads.AvailableDataReads._
import com.igeolise.traveltimesdk.dto.responses.GeoJsonResponse
import com.igeolise.traveltimesdk.dto.responses.common.GeocodingResponseProperties
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}

object GeocodingReads  {

  private val propertyReads: Reads[GeocodingResponseProperties] = (
    (__ \ "name").read[String] and
    (__ \ "label").read[String] and
    (__ \ "score").readNullable[Double] and
    (__ \ "house_number").readNullable[String] and
    (__ \ "street").readNullable[String] and
    (__ \ "region").readNullable[String] and
    (__ \ "region_code").readNullable[String] and
    (__ \ "neighbourhood").readNullable[String] and
    (__ \ "county").readNullable[String] and
    (__ \ "macroregion").readNullable[String] and
    (__ \ "city").readNullable[String] and
    (__ \ "country").readNullable[String] and
    (__ \ "country_code").readNullable[String] and
    (__ \ "continent").readNullable[String] and
    (__ \ "postcode").readNullable[String] and
    (__ \ "features").readNullable(mapInfoFeaturesReads)
  )(GeocodingResponseProperties.apply _)

  implicit val geocodingReads: Reads[GeoJsonResponse[GeocodingResponseProperties]] = GeoJsonReads.reads(propertyReads)
}
