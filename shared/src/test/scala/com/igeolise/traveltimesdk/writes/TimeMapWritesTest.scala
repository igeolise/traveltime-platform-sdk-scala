package com.igeolise.traveltimesdk.writes

import java.time.{ZoneId, ZonedDateTime}

import com.igeolise.traveltimesdk.json.writes.TimeMapWrites._
import com.igeolise.traveltimesdk.dto.requests.TimeMapRequest.ArrivalSearch
import com.igeolise.traveltimesdk.dto.requests.TimeMapRequest
import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.common.PublicTransportationParams
import com.igeolise.traveltimesdk.dto.requests.common.RangeParams.RangeParams
import com.igeolise.traveltimesdk.dto.requests.common.Transportation.PublicTransport
import org.scalatest._
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.Json

import scala.concurrent.duration._

class TimeMapWritesTest extends AnyFunSpec with Matchers{

  it("departure_searches and arrival_searches json request") {
    val trans = PublicTransport(PublicTransportationParams(Some(Duration(1, MINUTES)), None))
    val time = ZonedDateTime.of(2018,9,27,8,0,0,0,ZoneId.systemDefault())

    val departure =
      TimeMapRequest.DepartureSearch(
        "public transport from Trafalgar Square",
        Coords(51.507609, -0.128315),
        trans,
        time,
        Duration(900, SECONDS)
      )

    val arrival =
      ArrivalSearch(
        "public transport to Trafalgar Square",
        Coords(51.507609, -0.128315),
        trans,
        time,
        Duration(900, SECONDS),
        Some(RangeParams(
          enabled = true,
          1.hour
        )),
        None,
      )

    val timeMapRequest: TimeMapRequest = TimeMapRequest(Seq(departure), Seq(arrival), Seq(), Seq())
    val jsonRequest = Json.toJson(timeMapRequest)
    val jsonResource = TestUtils.resource("shared/src/test/resources/json/TimeMap/request/timeMapExampleRequest.json")

    jsonRequest should equal (Json.parse(jsonResource))
  }

}