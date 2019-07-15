package com.igeolise.traveltimesdk.reads

import java.time.ZonedDateTime

import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.responses.MapInfoResponse
import com.igeolise.traveltimesdk.dto.responses.MapInfoResponse._
import com.igeolise.traveltimesdk.json.reads.AvailableDataReads._
import org.scalatest.Matchers
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.{JsSuccess, Json}

class MapInfoReadsTest extends AnyFunSpec with Matchers {

  it("Testing mapInfo response + ZonedDateTime") {
    val json =
      TestUtils.resource("shared/src/test/resources/json/mapInfo/response/MapInfoResponse.json")
    val result = Json.parse(json).validate[MapInfoResponse]
    result shouldBe a[JsSuccess[_]]

    val time1 = ZonedDateTime.parse("2018-12-01T23:00:00+01:00")
    val time2 = ZonedDateTime.parse("2019-02-03T23:00:00+01:00")
    val time3 = ZonedDateTime.parse("2018-11-11T15:00:00+01:00")
    val time4 = ZonedDateTime.parse("2019-01-14T15:00:00+01:00")

    result.get shouldBe MapInfoResponse(
      Seq(
        Map(
          "th",
          Features(None, fares = false, postcodes = false)
        ),
        Map(
          "fi",
          Features(Some(PublicTransportData(time1, time2)), fares = false, postcodes = false)
        ),
        Map(
          "au_qld",
          Features(Some(PublicTransportData(time3, time4)), fares = false, postcodes = false)
        )
      ),
      Json.parse(json)
    )
  }
}
