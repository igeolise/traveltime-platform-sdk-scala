package com.igeolise.traveltimesdk.writes

import com.igeolise.traveltimesdk.json.reads.TimeMapReads._
import com.igeolise.traveltimesdk.json.writes.TimeMapWrites._
import com.igeolise.traveltimesdk.dto.requests.TimeMapRequest.ArrivalSearch
import com.igeolise.traveltimesdk.dto.requests.TimeMapRequest
import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.common.PublicTransportationParams
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.RangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.PublicTransport
import org.scalatest._
import play.api.libs.json.Json

class TimeMapWritesTest extends FunSpec with Matchers{

  it("departure_searches and arrival_searches json request") {
    val trans = PublicTransport(PublicTransportationParams(None, None))

    val departure =
      TimeMapRequest.DepartureSearch(
        "public transport from Trafalgar Square",
        Coords(51.507609, -0.128315),
        trans,
        "2018-09-27T08:00:00Z",
        900
      )

    val arrival =
      ArrivalSearch(
        "public transport to Trafalgar Square",
        Coords(51.507609, -0.128315),
        trans,
        "2018-09-27T08:00:00Z",
        900,
        Some(RangeParams(
          enabled = true,
          3600
        )),
        None,
      )

    val timeMapRequest: TimeMapRequest = TimeMapRequest(Seq(departure), Seq(arrival), Seq(), Seq())
    val jsonRequest = Json.toJson(timeMapRequest)
    val jsonResource = TestUtils.resource("shared/src/test/resources/json/TimeMap/request/timeMapExampleRequest.json")

    jsonRequest should equal (Json.parse(jsonResource))
  }

}