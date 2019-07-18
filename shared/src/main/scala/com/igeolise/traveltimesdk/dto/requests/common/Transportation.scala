package com.igeolise.traveltimesdk.dto.requests.common

import scala.concurrent.duration.FiniteDuration

sealed trait Transportation               { val transportType: String }

object Transportation {

  sealed trait CommonTransportation         extends Transportation
  sealed trait TimeFilterFastTransportation extends Transportation
  sealed trait FerryTransportation          extends Transportation with CommonTransportation {
    def parameters: FerryParams
  }
  sealed trait PublicTransportation         extends Transportation with CommonTransportation {
    def parameters: PublicTransportationParams
  }

  case class PublicTransportationParams(
    ptChangeDelay: Option[FiniteDuration] = None,
    walkingTime: Option[FiniteDuration]   = None
  )

  case class CyclingPublicTransportParams(
    cyclingToStationTime: Option[FiniteDuration] = None,
    parkingTime: Option[FiniteDuration]          = None,
    boardingTime: Option[FiniteDuration]         = None
  )

  case class FerryParams(boardingTime: Option[FiniteDuration] = None)

  case class DrivingTrainParams(
    drivingTimeToStation: Option[FiniteDuration]   = None,
    parkingTime: Option[FiniteDuration]            = None,
    ptChangeDelay: Option[FiniteDuration]          = None,
    walkingTimeFromStation: Option[FiniteDuration] = None
  )

  case class Bus(parameters: PublicTransportationParams) extends PublicTransportation {
    override val transportType = "bus"
  }

  case class Train(parameters: PublicTransportationParams) extends PublicTransportation {
    override val transportType = "train"
  }

  case class Coach(parameters: PublicTransportationParams) extends PublicTransportation {
    override val transportType = "coach"
  }

  case class PublicTransport(
    parameters: PublicTransportationParams
  ) extends PublicTransportation with TimeFilterFastTransportation {
    override val transportType = "public_transport"
  }

  case object Driving extends TimeFilterFastTransportation with CommonTransportation {
    override val transportType = "driving"
  }

  case object Cycling extends Transportation with CommonTransportation {
    override val transportType = "cycling"
  }

  case object Walking extends Transportation with CommonTransportation {
    override val transportType = "walking"
  }

  case class Ferry(parameters: FerryParams) extends FerryTransportation {
    override val transportType = "ferry"
  }

  case class CyclingPublicTransport(parameters: CyclingPublicTransportParams) extends CommonTransportation {
    override val transportType = "cycling+public_transport"
  }

  case class CyclingFerry(parameters: FerryParams) extends FerryTransportation {
    override val transportType = "cycling+ferry"
  }

  case class DrivingTrain(parameters: DrivingTrainParams) extends Transportation with CommonTransportation {
    override val transportType = "driving+train"
  }

  case class DrivingFerry(parameters: FerryParams) extends FerryTransportation {
    override val transportType = "driving+ferry"
  }
}