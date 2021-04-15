package com.traveltime.sdk.writes

import java.time.ZonedDateTime

import com.traveltime.sdk.json.writes.TimeMapWrites._
import com.traveltime.sdk.dto.requests.TimeMapRequest.{ArrivalSearch, SimpleLevelOfDetail}
import com.traveltime.sdk.dto.requests.TimeMapRequest
import com.traveltime.sdk.TestUtils
import com.traveltime.sdk.dto.common.Coords
import com.traveltime.sdk.dto.requests.common.CommonProperties.{Agencies, IsOnlyWalking}
import com.traveltime.sdk.dto.requests.common.RangeParams.RangeParams
import com.traveltime.sdk.dto.requests.common.Transportation.{PublicTransport, PublicTransportationParams}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.Json

import scala.concurrent.duration._

class TimeMapWritesTest extends AnyFunSpec with Matchers{

  it("departure_searches and arrival_searches json request") {
    val trans = PublicTransport(PublicTransportationParams(Some(Duration(1, MINUTES)), None))
    val time = ZonedDateTime.parse("2018-09-27T08:00:00+00:00")

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
        Some(Seq(IsOnlyWalking, Agencies)),
        Some(SimpleLevelOfDetail(SimpleLevelOfDetail.High))
      )

    val timeMapRequest: TimeMapRequest = TimeMapRequest(Seq(departure), Seq(arrival), Seq(), Seq())
    val jsonRequest = Json.toJson(timeMapRequest)
    val jsonResource = TestUtils.resource("shared/src/test/resources/json/TimeMap/request/timeMapExampleRequest.json")

    jsonRequest should equal (Json.parse(jsonResource))
  }

}