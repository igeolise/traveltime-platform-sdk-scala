package com.igeolise.traveltimesdk.reads

import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.responses.SupportedLocationsResponse
import com.igeolise.traveltimesdk.dto.responses.SupportedLocationsResponse.Location
import com.igeolise.traveltimesdk.json.reads.AvailableDataReads._
import org.scalatest.Matchers
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.{JsSuccess, Json}

class SupportedLocationsTest extends AnyFunSpec with Matchers {

  it("parse departurePostcodesResponse response") {
    val json =
      TestUtils.resource("shared/src/test/resources/json/SupportedLocations/response/supportedLocationsResponse.json")
    val result = Json.parse(json).validate[SupportedLocationsResponse]
    result shouldBe a[JsSuccess[_]]

    result.get shouldBe SupportedLocationsResponse(
      Seq(
        Location("Kaunas", "lt"),
        Location("London", "gb"),
        Location("Bangkok", "th")
      ),
      Seq("Lisbon"),
      Json.parse(json)
    )
  }

}
