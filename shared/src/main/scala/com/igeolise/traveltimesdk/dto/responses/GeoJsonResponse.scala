package com.igeolise.traveltimesdk.dto.responses

import play.api.libs.json.JsValue

case class GeoJsonResponse[A](feature: GeoJsonResponse.Feature[A]) {
  def inMap[B](fun: GeoJsonResponse.SingleFeature[A] => B): GeoJsonResponse[B] = feature match {
    case f: GeoJsonResponse.SingleFeature[A] =>
      GeoJsonResponse(f.copy(properties = fun(f)))
    case GeoJsonResponse.FeatureCollection(features) =>
      GeoJsonResponse(GeoJsonResponse.FeatureCollection(
        features.map(f => f.copy(properties = fun(f)))
      ))
  }

  def map[B](fun: GeoJsonResponse.SingleFeature[A] => B): Seq[B] = feature match {
    case f: GeoJsonResponse.SingleFeature[A] => Seq(fun(f))
    case GeoJsonResponse.FeatureCollection(features) => features.map(fun(_))
  }

  def size: Int = feature match {
    case _: GeoJsonResponse.SingleFeature[A] => 1
    case GeoJsonResponse.FeatureCollection(features) => features.size
  }

  def isEmpty: Boolean = size == 0
  def nonEmpty: Boolean = !isEmpty
}


object GeoJsonResponse {
  sealed trait Feature[A]

  case class SingleFeature[A](geometry: Geometry, properties: A) extends Feature[A]
  case class FeatureCollection[A](features: Seq[SingleFeature[A]]) extends Feature[A]

  sealed trait Geometry
  case class Point(lat: Double, lng: Double) extends Geometry

  case class GeoJsonResult[A](res: GeoJsonResponse[A], raw: JsValue)
}