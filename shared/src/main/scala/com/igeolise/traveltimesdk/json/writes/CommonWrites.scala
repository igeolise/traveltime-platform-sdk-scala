package com.igeolise.traveltimesdk.json.writes

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.common.ZoneSearches.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.{PropertyType, TimeFilterZonesProperty}
import com.igeolise.traveltimesdk.dto.requests.common._
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.{FullRangeParams, RangeParams}
import com.igeolise.traveltimesdk.dto.requests.common.Transportation._
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.concurrent.duration.FiniteDuration

object CommonWrites {

  implicit val timeMapPropertiesWrites: Writes[Seq[PropertyType]] = new Writes[Seq[PropertyType]] {
    override def writes(props: Seq[PropertyType]): JsValue = Json.arr(props.map(_.propertyType)).value.head
  }

  implicit class extractTime(self: Option[FiniteDuration]) {
    def toSeconds: Option[Int] = self.map(time => time.toSeconds.toInt)
  }

  val finiteDurationToSecondsWrites: Writes[FiniteDuration] = Writes.IntWrites.contramap(_.toSeconds.toInt)

  implicit class formatDate(self: ZonedDateTime) {
    def formatDate: String = self.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
  }

  def parameterlessTransportationJson(transportation: Transportation): JsObject =
     JsObject(Seq("type" -> Json.toJson(transportation.transportType)))

  implicit val formattedDateWrites: Writes[ZonedDateTime] = Writes.StringWrites.contramap(_.formatDate)

  implicit val fullRangeParamsWrites: Writes[FullRangeParams] = (
    (__ \ "enabled").write[Boolean] and
    (__ \ "max_results").write[Int] and
    (__ \ "width").write[FiniteDuration](finiteDurationToSecondsWrites)
  ) (unlift(FullRangeParams.unapply))

  implicit val rangeParamsWrites: Writes[RangeParams] = (
    (__ \ "enabled").write[Boolean] and
    (__ \ "width").write[FiniteDuration](finiteDurationToSecondsWrites)
  ) (unlift(RangeParams.unapply))

  implicit val publicTransportWrites: Writes[PublicTransportation] = (
    (__ \ "type").write[String] and
    (__ \ "walking_time").writeNullable[Int] and
    (__ \ "pt_change_delay").writeNullable[Int]
  ) ((m: PublicTransportation) => (
      m.transportType,
      m.parameters.walkingTime.toSeconds,
      m.parameters.ptChangeDelay.toSeconds
    )
  )

  implicit val drivingTrainWrites: Writes[Transportation.DrivingTrain] = (
    (__ \ "type").write[String] and
    (__ \ "pt_change_delay").writeNullable[Int] and
    (__ \ "driving_time_to_station").writeNullable[Int] and
    (__ \ "parking_time").writeNullable[Int] and
    (__ \ "walking_time_from_station").writeNullable[Int]
  ) ((m: Transportation.DrivingTrain) => (
    m.transportType,
    m.parameters.ptChangeDelay.toSeconds,
    m.parameters.drivingTimeToStation.toSeconds,
    m.parameters.parkingTime.toSeconds,
    m.parameters.walkingTimeFromStation.toSeconds
  ))

  implicit val ferryWrites: Writes[FerryTransportation] = (
    (__ \ "type").write[String] and
    (__ \ "boarding_time").writeNullable[Int]
  ) ((m: FerryTransportation) => (m.transportType, m.parameters.boardingTime.toSeconds))

  implicit val cyclingPublicTransportWrites: Writes[CyclingPublicTransport] = (
    (__ \ "type").write[String] and
    (__ \ "cycling_time_to_station").writeNullable[Int] and
    (__ \ "parking_time").writeNullable[Int] and
    (__ \ "boarding_time").writeNullable[Int]
  ) ((m: CyclingPublicTransport) => (
      m.transportType,
      m.parameters.cyclingToStationTime.toSeconds,
      m.parameters.parkingTime.toSeconds,
      m.parameters.boardingTime.toSeconds
    )
  )

  implicit val commonTransportationWrites: Writes[CommonTransportation] = Writes[CommonTransportation] {
    case c: FerryTransportation    => ferryWrites.writes(c)
    case c: PublicTransportation   => publicTransportWrites.writes(c)
    case c: CyclingPublicTransport => cyclingPublicTransportWrites.writes(c)
    case c: DrivingTrain           => drivingTrainWrites.writes(c)
    case c: CommonTransportation   => parameterlessTransportationJson(c)
  }

  implicit val timeFilterFastTransportationWrites: Writes[TimeFilterFastTransportation] =
    Writes[TimeFilterFastTransportation](parameterlessTransportationJson)

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
    (__ \ "transportation").write[CommonTransportation] and
    (__ \ "arrival_time").write[ZonedDateTime] and
    (__ \ "travel_time").write[FiniteDuration](finiteDurationToSecondsWrites) and
    (__ \ "reachable_postcodes_threshold").write[Double] and
    (__ \ "properties").write[Seq[TimeFilterZonesProperty]] and
    (__ \ "range").writeNullable[FullRangeParams]
  )(unlift(ArrivalSearch.unapply))

  implicit val timeFilterZonesDepartureWrites: Writes[DepartureSearch] = (
    (__ \ "id").write[String] and
    (__ \ "coords").write[Coords] and
    (__ \ "transportation").write[CommonTransportation] and
    (__ \ "departure_time").write[ZonedDateTime] and
    (__ \ "travel_time").write[FiniteDuration](finiteDurationToSecondsWrites) and
    (__ \ "reachable_postcodes_threshold").write[Double] and
    (__ \ "properties").write[Seq[TimeFilterZonesProperty]] and
    (__ \ "range").writeNullable[FullRangeParams]
  )(unlift(DepartureSearch.unapply))
}