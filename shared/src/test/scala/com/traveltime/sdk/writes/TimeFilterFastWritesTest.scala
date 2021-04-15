package com.traveltime.sdk.writes

import com.traveltime.sdk.TestUtils
import com.traveltime.sdk.dto.common.Coords
import com.traveltime.sdk.dto.requests.common.CommonProperties.{Fares, TravelTime}
import com.traveltime.sdk.dto.requests.common.Location
import com.traveltime.sdk.dto.requests.common.Transportation._
import com.traveltime.sdk.dto.requests.timefilter.TimeFilterFastRequest
import com.traveltime.sdk.dto.requests.timefilter.TimeFilterFastRequest.{ArrivalSearch, ManyToOne, OneToMany, WeekdayMorning}
import com.traveltime.sdk.json.writes.timefilter.TimeFilterWrites._
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.Json
import com.traveltime.sdk.json.writes.timefilter.TimeFilterFastWrites._

import scala.concurrent.duration._

class TimeFilterFastWritesTest extends AnyFunSpec with Matchers {

  it("arrival_searches for TimeFilter Postcodes") {
    val fileResource = TestUtils.resource("shared/src/test/resources/json/TimeFilterFast/request/TimeFilterFastRequest.json")

    val locations: Seq[Location] = Seq(
      Location("London center", Coords(51.508930, -0.131387)),
      Location("Hyde Park", Coords(51.508824, -0.167093)),
      Location("ZSL London Zoo", Coords(51.536067, -0.153596))
    )

    val manyToOne = ManyToOne(
      "arrive-at many-to-one search example",
      "London center",
      Seq(
        "Hyde Park",
        "ZSL London Zoo"
      ),
      PublicTransport(PublicTransportationParams()),
      0.5.hour,
      WeekdayMorning,
      Seq(
        TravelTime,
        Fares
      )
    )

    val oneToMany = OneToMany(
      "arrive-at one-to-many search example",
      "London center",
      Seq(
        "Hyde Park",
        "ZSL London Zoo"
      ),
      TimeFilterFastDriving,
      30.minutes,
      WeekdayMorning,
      Seq(
        TravelTime,
        Fares
      )
    )

    val request = TimeFilterFastRequest(
      locations,
      ArrivalSearch(
        Seq(manyToOne),
        Seq(oneToMany)
      )
    )

    Json.toJson(request) shouldBe Json.parse(fileResource)
  }
}
