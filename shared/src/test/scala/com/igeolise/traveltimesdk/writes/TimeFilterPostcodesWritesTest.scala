package com.igeolise.traveltimesdk.writes

import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.PropertyType.{Distance, TravelTime}
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.{PublicTransport, PublicTransportationParams}
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterPostcodesRequest
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterPostcodesWrites._
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.Json

import scala.concurrent.duration._

class TimeFilterPostcodesWritesTest extends AnyFunSpec with Matchers  {

  it("departure_searches for TimeFilter Postcodes") {
    val transport = PublicTransport(PublicTransportationParams(None, None))
    val postcodesDeparture: TimeFilterPostcodesRequest.DepartureSearch =
      TimeFilterPostcodesRequest.DepartureSearch(
        "public transport from Trafalgar Square",
        Coords(51.507609, -0.128315),
        transport,
        "2018-09-27T08:00:00Z",
        Duration(1800, SECONDS),
        None,
        Seq(TravelTime, Distance)
      )
    val postcodesArrival : TimeFilterPostcodesRequest.ArrivalSearch =
      TimeFilterPostcodesRequest.ArrivalSearch(
        "public transport to Trafalgar Square",
        Coords(51.507609, -0.128315),
        transport,
        "2018-09-27T08:00:00Z",
        Duration(1800, SECONDS),
        None,
        Seq(TravelTime, Distance)
      )
    val postcodesRequest = TimeFilterPostcodesRequest(Seq(postcodesDeparture), Seq(postcodesArrival))

    val jsonResource = TestUtils.resource("shared/src/test/resources/json/TimeFilterPostcodes/request/PostcodesRequest.json")
    val timeFilterJson = Json.toJson(postcodesRequest)

    timeFilterJson should equal (Json.parse(jsonResource))
  }
}
