package com.igeolise.traveltimesdk.reads

import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.common.{TravelTime, Zone, ZoneSearchProperties}
import com.igeolise.traveltimesdk.dto.responses.timefilter.{TimeFilterDistrictsResponse, TimeFilterSectorsResponse}
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterDistrictsReads._
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterSectorsReads._
import org.scalatest.Matchers
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.{JsSuccess, Json}

import scala.concurrent.duration._

class TimeFilterZonesReadsTest extends AnyFunSpec with Matchers {

  it("parse districts arrivals response") {
    val json = TestUtils.resource("shared/src/test/resources/json/TimeFilterDistricts/response/DistrictsResponse.json")
    val result = Json.parse(json).validate[TimeFilterDistrictsResponse]

    result shouldBe a [JsSuccess[_]]

    result.get shouldBe TimeFilterDistrictsResponse(
      Vector(
        TimeFilterDistrictsResponse.SingleSearchResult(
          "public transport to Trafalgar Square",
          Vector(
            Zone(
              "wc2n",
              ZoneSearchProperties(
                Some(TravelTime(10.seconds, 199.seconds, 142.seconds, 164.seconds)),
                Some(TravelTime(10.seconds, 527.seconds, 324.seconds, 397.seconds)),
                Some(12.571428571428573)
              )
            ),
            Zone(
              "sw1a",
              ZoneSearchProperties(
                Some(TravelTime(86.seconds, 194.seconds, 147.seconds, 155.seconds)),
                Some(TravelTime(86.seconds, 645.seconds, 379.seconds, 396.seconds)),
                Some(12.418300653594772)
              )
            )
          )
        )
      ),
      Json.parse(json)
    )
  }

  it("parse sectors arrivals response") {
    val json = TestUtils.resource("shared/src/test/resources/json/TimeFilterSectors/response/SectorsArrivalsResponse.json")
    val result = Json.parse(json).validate[TimeFilterSectorsResponse]

    result shouldBe a [JsSuccess[_]]

    result.get shouldBe TimeFilterSectorsResponse(
      Seq(
        TimeFilterSectorsResponse.SingleSearchResult(
          "public transport to Trafalgar Square",
          Seq(
            Zone(
              "wc2n5",
              ZoneSearchProperties(
                Some(TravelTime(10.seconds, 113.seconds, 78.seconds, 81.seconds)),
                Some(TravelTime(10.seconds, 113.seconds, 78.seconds, 81.seconds)),
                Some(15.217391304347828)
              )
            )
          )
        )
      ),
      Json.parse(json)
    )
  }
}
