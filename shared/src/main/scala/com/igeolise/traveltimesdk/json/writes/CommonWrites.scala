package com.igeolise.traveltimesdk.json.writes

import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.common.ZoneSearches.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.{PropertyType, TimeFilterZonesProperty}
import com.igeolise.traveltimesdk.dto.requests.common.{FerryTransportation, Location, PublicTransportation, Transportation}
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.{FullRangeParams, RangeParams}
import play.api.libs.functional.syntax._
import play.api.libs.json._

object CommonWrites {

  implicit val timeMapPropertiesWrites: Writes[Seq[PropertyType]] = new Writes[Seq[PropertyType]] {
    override def writes(props: Seq[PropertyType]): JsValue = Json.arr(props.map(_.propertyType)).value.head
  }

  implicit val fullRangeParamsWrites: Writes[FullRangeParams] = (
    (__ \ "enabled").write[Boolean] and
    (__ \ "max_results").write[Int] and
    (__ \ "width").write[Int]
  ) (unlift(FullRangeParams.unapply))

  implicit val rangeParamsWrites: Writes[RangeParams] = (
    (__ \ "enabled").write[Boolean] and
    (__ \ "width").write[Int]
  ) (unlift(RangeParams.unapply))

  implicit val publicTransportWrites: Writes[PublicTransportation] = (
    (__ \ "type").write[String] and
    (__ \ "walking_time").writeNullable[Int] and
    (__ \ "pt_change_delay").writeNullable[Int]
  ) ((m: PublicTransportation) =>
    (m.transportType, m.parameters.walkingTime, m.parameters.ptChangeDelay))

  implicit val drivingTrainWrites: Writes[Transportation.DrivingTrain] = (
    (__ \ "type").write[String] and
    (__ \ "pt_change_delay").writeNullable[Int] and
    (__ \ "driving_time_to_station").writeNullable[Int] and
    (__ \ "parking_time").writeNullable[Int] and
    (__ \ "walking_time_from_station").writeNullable[Int]
  ) ((m: Transportation.DrivingTrain) =>
    (m.transportType, m.params.ptChangeDelay, m.params.drivingTimeToStation,
      m.params.parkingTime, m.params.walkingTimeFromStation)
  )

  implicit val ferryWrites: Writes[FerryTransportation] = (
    (__ \ "type").write[String] and
    (__ \ "boarding_time").writeNullable[Int]
  ) ((m: FerryTransportation) => (m.transportType, m.parameters.boardingTime))

  implicit val transportationWrites: Writes[Transportation] = Writes[Transportation] {
    case m: PublicTransportation => publicTransportWrites.writes(m)
    case m: Transportation.DrivingTrain => drivingTrainWrites.writes(m)
    case m: FerryTransportation => ferryWrites.writes(m)
    case m: Transportation => JsObject(Seq("type" -> Json.toJson(m.transportType)))
  }

  implicit val coordsWrites: Writes[Coords] = (
    (__ \ "lat").write[Double] and
    (__ \ "lng").write[Double]
  ) (unlift(Coords.unapply))

  implicit val locationWrites: Writes[Location] = (
    (__ \ "id").write[String] and
    (__ \ "coords").write[Coords]
  )(unlift(Location.unapply))

  implicit val timeFilterZonesArrivalWrites: Writes[ArrivalSearch] = (
    (__ \ "id").write[String] and
    (__ \ "coords").write[Coords] and
    (__ \ "transportation").write[Transportation] and
    (__ \ "arrival_time").write[String] and
    (__ \ "travel_time").write[Int] and
    (__ \ "reachable_postcodes_threshold").write[Double] and
    (__ \ "properties").write[Seq[TimeFilterZonesProperty]] and
    (__ \ "range").writeNullable[FullRangeParams]
  )(unlift(ArrivalSearch.unapply))

  implicit val timeFilterZonesDepartureWrites: Writes[DepartureSearch] = (
    (__ \ "id").write[String] and
    (__ \ "coords").write[Coords] and
    (__ \ "transportation").write[Transportation] and
    (__ \ "departure_time").write[String] and
    (__ \ "travel_time").write[Int] and
    (__ \ "reachable_postcodes_threshold").write[Double] and
    (__ \ "properties").write[Seq[TimeFilterZonesProperty]] and
    (__ \ "range").writeNullable[FullRangeParams]
  )(unlift(DepartureSearch.unapply))
}