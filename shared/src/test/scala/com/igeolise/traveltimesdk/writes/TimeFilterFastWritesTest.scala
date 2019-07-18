package com.igeolise.traveltimesdk.writes

import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.PropertyType.{Fares, TravelTime}
import com.igeolise.traveltimesdk.dto.requests.common.Location
import com.igeolise.traveltimesdk.dto.requests.common.Transportation._
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterFastRequest
import com.igeolise.traveltimesdk.dto.requests.timefilter.TimeFilterFastRequest.{ArrivalSearch, ManyToOne, OneToMany, WeekdayMorning}
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterWrites._
import org.scalatest.Matchers
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.Json
import com.igeolise.traveltimesdk.json.writes.timefilter.TimeFilterFastWrites._

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
      Driving,
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
