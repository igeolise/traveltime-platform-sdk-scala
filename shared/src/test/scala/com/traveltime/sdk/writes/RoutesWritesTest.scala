package com.traveltime.sdk.writes

import java.time.ZonedDateTime

import com.traveltime.sdk.TestUtils
import com.traveltime.sdk.dto.common.Coords
import com.traveltime.sdk.dto.requests.RoutesRequest
import com.traveltime.sdk.dto.requests.RoutesRequest.{ArrivalSearch, DepartureSearch}
import com.traveltime.sdk.dto.requests.common.CommonProperties._
import com.traveltime.sdk.dto.requests.common.RangeParams.FullRangeParams
import com.traveltime.sdk.dto.requests.common.Transportation.{DrivingParams, PublicTransportationParams}
import com.traveltime.sdk.dto.requests.common.{Location, Transportation}
import com.traveltime.sdk.json.writes.RoutesWrites._
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.Json

import scala.concurrent.duration._

class RoutesWritesTest extends AnyFunSpec with Matchers {

  it("departure and arrival searches for Routes") {
    val locations: Seq[Location] = Seq(
      Location("London center", Coords(51.508930, -0.131387)),
      Location("Hyde Park", Coords(51.508824, -0.167093)),
      Location("ZSL London Zoo", Coords(51.536067, -0.153596))
    )
    val time = ZonedDateTime.parse("2018-10-02T08:00:00+00:00")

    val routesDepartures = DepartureSearch(
      "departure search example",
      "London center",
      Seq("Hyde Park", "ZSL London Zoo"),
      Transportation.Driving(DrivingParams(Some(true))),
      time,
      Seq(TravelTime, Distance, Route),
      None
    )

    val routesArrivals = ArrivalSearch(
      "arrival search example",
      Seq("Hyde Park", "ZSL London Zoo"),
      "London center",
      Transportation.PublicTransport(PublicTransportationParams()),
      time,
      Seq(TravelTime, Distance, Route, Fares),
      Some(FullRangeParams(enabled = true, 1, 30.minutes))
    )

    val routesRequest = RoutesRequest(locations, Seq(routesDepartures), Seq(routesArrivals))
    val jsonResource = TestUtils.resource("shared/src/test/resources/json/Routes/request/routesRequest.json")

    val routesJson = Json.toJson(routesRequest)
    val resourceJson = Json.parse(jsonResource)

    routesJson should equal (resourceJson)
  }
}
