package com.traveltime.sdk.writes

import java.time.ZonedDateTime

import com.traveltime.sdk.TestUtils
import com.traveltime.sdk.dto.common.Coords
import com.traveltime.sdk.dto.requests.common.CommonProperties.{Distance, DistanceBreakdown, Fares, TravelTime}
import com.traveltime.sdk.dto.requests.common.RangeParams.FullRangeParams
import com.traveltime.sdk.dto.requests.common.Transportation._
import com.traveltime.sdk.dto.requests.common.{CommonProperties, Location}
import com.traveltime.sdk.dto.requests.timefilter.TimeFilterRequest
import com.traveltime.sdk.dto.requests.timefilter.TimeFilterRequest.DepartureSearch
import com.traveltime.sdk.json.writes.timefilter.TimeFilterWrites._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.duration.{Duration, SECONDS, _}

class TimeFilterWritesTest extends AnyFunSpec with Matchers {
  it("TimeFilterWritesTest: locations, departure_searches and arrival_searches json request") {
    val locations: Seq[Location] = Seq(
      Location("London center", Coords(51.508930, -0.131387)),
      Location("Hyde Park", Coords(51.508824, -0.167093)),
      Location("ZSL London Zoo", Coords(51.536067, -0.153596))
    )

    val time = ZonedDateTime.parse("2018-09-27T08:00:00Z")

    val timeFilterDepartures = DepartureSearch(
      "forward search example",
      "London center",
      Seq("Hyde Park", "ZSL London Zoo" ),
      Bus(PublicTransportationParams(None, None)),
      Duration(1800, SECONDS),
      time,
      Some(FullRangeParams(enabled = true, 3, 10.minutes)),
      Seq(CommonProperties.TravelTime)
    )

    val timeFilterArrivals = TimeFilterRequest.ArrivalSearch(
      "backward search example",
      Seq("Hyde Park", "ZSL London Zoo"),
      "London center",
      PublicTransport(PublicTransportationParams(None, None)),
      Duration(1900, SECONDS),
      time,
      None,
      Seq(TravelTime, Distance, DistanceBreakdown, Fares)
    )

    val jsonResource = TestUtils.resource("shared/src/test/resources/json/TimeFilter/request/timeFilterRequest.json")
    val timeFilterRequest = TimeFilterRequest(locations, Seq(timeFilterDepartures), Seq(timeFilterArrivals))
    val timeFilterJson = Json.toJson(timeFilterRequest)

    timeFilterJson shouldBe Json.parse(jsonResource)
  }

  it("Make json request for time-filter request with cycling+public_transport transportation") {
      val locations: Seq[Location] = Seq(
        Location("Musee Du Cannabis", Coords(52.37213452952525, 4.897260664233954)),
        Location("Amsterdam center", Coords(52.3680, 4.9036)),
        Location("Science Museum", Coords(52.37421715421411, 4.912337064743042))
      )

      val time = ZonedDateTime.parse("2019-07-08T08:00:00+02:00")

      val timeFilterDepartures = DepartureSearch(
        "forward search example",
        "Amsterdam center",
        Seq("Musee Du Cannabis", "Science Museum"),
        CyclingPublicTransport(
          CyclingPublicTransportParams(
            Some(5.minutes),
            Some(10.minutes),
            Some(15.minutes),
            Some(30.seconds),
            Some(15.minutes)
          )
        ),
        Duration(1800, SECONDS),
        time,
        Some(FullRangeParams(enabled = true, 3, 10.minutes)),
        Seq(CommonProperties.TravelTime)
      )

      val timeFilterArrivals = TimeFilterRequest.ArrivalSearch(
        "backward search example",
        Seq("Musee Du Cannabis", "Science Museum"),
        "Amsterdam center",
        CyclingPublicTransport(
          CyclingPublicTransportParams(
            Some(5.minutes),
            Some(10.minutes),
            Some(15.minutes),
            Some(30.seconds),
            Some(15.minutes)
          )
        ),
        Duration(1900, SECONDS),
        time,
        None,
        Seq(TravelTime, Distance, DistanceBreakdown, Fares)
      )

      val jsonResource = TestUtils.resource("shared/src/test/resources/json/TimeFilter/request/timeFilterRequest-cycling-pt.json")

      val timeFilterRequest = TimeFilterRequest(locations, Seq(timeFilterDepartures), Seq(timeFilterArrivals))
      val timeFilterJson: JsValue = Json.toJson(timeFilterRequest)
      val parsed: JsValue = Json.parse(jsonResource)

      timeFilterJson shouldBe parsed
  }
}
