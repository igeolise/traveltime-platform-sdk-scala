package com.traveltime.sdk.json.reads

import com.traveltime.sdk.dto.responses.GeoJsonResponse
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}

object GeoJsonReads {

  def reads[A](propertyReads: Reads[A]): Reads[GeoJsonResponse[A]] = (__ \ "type").read[String].flatMap({
    case "FeatureCollection" => GeoJsonReads.readFeatureCollection(propertyReads).map(x => GeoJsonResponse(x))
    case "Feature" => GeoJsonReads.readSingleFeature(propertyReads).map(x =>GeoJsonResponse(x))
  })

  val readPoint: Reads[GeoJsonResponse.Point] = (__ \ "coordinates").read[Vector[Double]].map(v => {
    val lat = v(1)
    val lng = v(0)
    GeoJsonResponse.Point(lat, lng)
  })

  val geometryReads: Reads[GeoJsonResponse.Geometry] = (__ \ "type").read[String].flatMap({
    case "Point" => readPoint.map(_.asInstanceOf[GeoJsonResponse.Geometry])
  })

  def readSingleFeature[A](propertyReads: Reads[A]): Reads[GeoJsonResponse.SingleFeature[A]] = (
    (__ \ "geometry").read(geometryReads) and
    (__ \ "properties").read(propertyReads)
  )(GeoJsonResponse.SingleFeature.apply[A] _)

  def readFeatureCollection[A](propertyReads: Reads[A]): Reads[GeoJsonResponse.FeatureCollection[A]] =
    (__ \ "features").read[Seq[GeoJsonResponse.SingleFeature[A]]](Reads.seq(readSingleFeature(propertyReads)))
      .map(GeoJsonResponse.FeatureCollection(_))
}