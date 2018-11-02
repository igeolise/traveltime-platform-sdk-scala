package com.igeolise.traveltimesdk.writes

import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterReads._
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterWrites._
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterRequest
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterRequest.DepartureSearch
import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.PropertyType.{distance, distanceBreakdown, fares, travelTime}
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.FullRangeParams
import com.igeolise.traveltimesdk.dto.requests.common.{CommonProperties, Location, PublicTransportationParams, Transportation}
import org.scalatest.{FunSpec, Matchers}
import play.api.libs.json.Json

class TimeFilterWritesTest extends FunSpec with Matchers {
  it("TimeFilterWritesTest: locations, departure_searches and arrival_searches json request") {
    val locations: Seq[Location] = Seq(
      Location("London center", Coords(51.508930, -0.131387)),
      Location("Hyde Park", Coords(51.508824, -0.167093)),
      Location("ZSL London Zoo", Coords(51.536067, -0.153596))
    )

    val timeFilterDepartures = DepartureSearch(
      "forward search example",
      "London center",
      Seq("Hyde Park", "ZSL London Zoo" ),
      Transportation.Bus(PublicTransportationParams(None, None)),
      1800,
      "2018-09-27T08:00:00Z",
      Some(FullRangeParams(enabled = true, 3, 600)),
      Seq(CommonProperties.PropertyType.travelTime)
    )

    val timeFilterArrivals = TimeFilterRequest.ArrivalSearch(
      "backward search example",
      Seq("Hyde Park", "ZSL London Zoo"),
      "London center",
      Transportation.PublicTransport(PublicTransportationParams(None, None)),
      1900,
      "2018-09-27T08:00:00Z",
      None,
      Seq(travelTime, distance, distanceBreakdown, fares)
    )

    val jsonResource = TestUtils.resource("shared/src/test/resources/json/TimeFilter/request/timeFilterRequest.json")
    val timeFilterRequest = TimeFilterRequest(locations, Seq(timeFilterDepartures), Seq(timeFilterArrivals))
    val timeFilterJson = Json.toJson(timeFilterRequest)

    timeFilterJson should equal (Json.parse(jsonResource))

  }
}
