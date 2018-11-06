package com.igeolise.traveltimesdk.writes

import java.time.{ZoneId, ZonedDateTime}

import com.igeolise.traveltimesdk.json.writes.RoutesWrites._
import com.igeolise.traveltimesdk.dto.requests.RoutesRequest
import com.igeolise.traveltimesdk.dto.requests.RoutesRequest.{ArrivalSearch, DepartureSearch}
import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.PropertyType
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.PropertyType.{distance, fares, route, travelTime}
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.requests.common.{Location, PublicTransportationParams, Transportation}
import org.scalatest.{FunSpec, Matchers}
import play.api.libs.json.Json

class RoutesWritesTest extends FunSpec with Matchers {
  it("departure and arrival searches for Routes") {
    val locations: Seq[Location] = Seq(
      Location("London center", Coords(51.508930, -0.131387)),
      Location("Hyde Park", Coords(51.508824, -0.167093)),
      Location("ZSL London Zoo", Coords(51.536067, -0.153596))
    )
    val time = ZonedDateTime.of(2018,10,2,8,0,0,0,ZoneId.systemDefault())

    val routesDepartures = DepartureSearch(
      "departure search example",
      "London center",
      Seq("Hyde Park", "ZSL London Zoo"),
      Transportation.Driving,
      time,
      Seq(PropertyType.travelTime, distance, route),
      None
    )

    val routesArrivals = ArrivalSearch(
      "arrival search example",
      Seq("Hyde Park", "ZSL London Zoo"),
      "London center",
      Transportation.PublicTransport(PublicTransportationParams()),
      time,
      Seq(travelTime, distance, route, fares),
      Some(FullRangeParams(enabled = true, 1, 1800))
    )

    val routesRequest = RoutesRequest(locations, Seq(routesDepartures), Seq(routesArrivals))
    val jsonResource = TestUtils.resource("shared/src/test/resources/json/Routes/request/routesRequest.json")

    val routesJson = Json.toJson(routesRequest)
    val resourceJson = Json.parse(jsonResource)

    routesJson should equal (resourceJson)
  }
}
