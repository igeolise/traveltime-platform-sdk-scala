package com.igeolise.traveltimesdk.dto.requests.common

import enumeratum.{Enum, EnumEntry}

import scala.collection.immutable
import scala.concurrent.duration.FiniteDuration

sealed trait Transportation extends EnumEntry { val transportType: String }
sealed trait CommonTransportation         extends Transportation
sealed trait TimeFilterFastTransportation extends Transportation

sealed trait FerryTransportation          extends Transportation with CommonTransportation
  {val parameters: FerryParams}

sealed trait PublicTransportation         extends Transportation with CommonTransportation
  {val parameters: PublicTransportationParams}

case class PublicTransportationParams(
  ptChangeDelay: Option[FiniteDuration] = None,
  walkingTime: Option[FiniteDuration] = None
)

case class FerryParams(boardingTime: Option[FiniteDuration] = None){
}

case class DrivingTrainParams(
  drivingTimeToStation: Option[FiniteDuration] = None,
  parkingTime: Option[FiniteDuration] = None,
  ptChangeDelay: Option[FiniteDuration] = None,
  walkingTimeFromStation: Option[FiniteDuration] = None
)

object Transportation extends Enum[Transportation] {
  val values: immutable.IndexedSeq[Transportation] = findValues

  case object CyclingFerry extends Transportation {
    override val transportType: String = "cycling+ferry"
  }

  case class CyclingFerry(params: FerryParams) extends FerryTransportation {
    override val transportType: String = "cycling+ferry"
    override val parameters: FerryParams = params
  }

  case object PublicTransport extends TimeFilterFastTransportation with CommonTransportation {
    override val transportType: String = "public_transport"
  }

  case class PublicTransport(params: PublicTransportationParams) extends PublicTransportation {
    override val transportType = "public_transport"
    override val parameters: PublicTransportationParams = params
  }

  case object Coach extends Transportation{
    override val transportType: String = "coach"
  }

  case class Coach(params: PublicTransportationParams) extends PublicTransportation {
    override val transportType = "coach"
    override val parameters: PublicTransportationParams = params
  }

  case object Bus extends Transportation{
    override val transportType: String = "bus"
  }

  case class Bus(params: PublicTransportationParams) extends PublicTransportation {
    override val transportType = "bus"
    override val parameters: PublicTransportationParams = params
  }

  case object Train extends Transportation{
    override val transportType: String = "train"
  }

  case class Train(params: PublicTransportationParams) extends PublicTransportation {
    override val transportType = "train"
    override val parameters: PublicTransportationParams = params
  }

  case object DrivingTrain extends Transportation{
    override val transportType: String = "driving+train"
  }

  case class DrivingTrain(params: DrivingTrainParams) extends Transportation with CommonTransportation {
    override val transportType = "driving+train"
  }

  case object Ferry extends Transportation {
    override val transportType: String = "ferry"
  }

  case class Ferry(params: FerryParams) extends FerryTransportation {
    override val transportType = "ferry"
    override val parameters: FerryParams = params
  }

  case object Cycling extends Transportation with CommonTransportation {
    override val transportType = "cycling"
  }

  case object Driving extends TimeFilterFastTransportation with CommonTransportation {
    override val transportType = "driving"
  }

  case object Walking extends Transportation with CommonTransportation {
    override val transportType = "walking"
  }

  case object DrivingFerry extends Transportation {
    override val transportType: String = "driving+ferry"
  }

  case class DrivingFerry(params: FerryParams) extends FerryTransportation {
    override val transportType = "driving+ferry"
    override val parameters: FerryParams = params
  }

  case object DrivingPublicTransport extends TimeFilterFastTransportation {
    override val transportType = "driving+public_transport"
  }
}