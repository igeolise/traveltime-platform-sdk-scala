package com.igeolise.traveltimesdk.reads

import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterSectorsReads._
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterDistrictsReads._
import com.igeolise.traveltimesdk.dto.responses.timefilter.{TimeFilterDistrictsResponse, TimeFilterSectorsResponse}
import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.common.{TravelTime, Zone, ZoneSearchProperties}
import org.scalatest.{FunSpec, Matchers}
import play.api.libs.json.{JsSuccess, Json}

class TimeFilterZonesReadsTest extends FunSpec with Matchers {
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
                Some(TravelTime(10,199,142,164)),
                Some(TravelTime(10,527,324,397)),
                Some(12.571428571428573)
              )
            ),
            Zone(
              "sw1a",
              ZoneSearchProperties(
                Some(TravelTime(86,194,147,155)),
                Some(TravelTime(86,645,379,396)),
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
                Some(TravelTime(10,113,78,81)),
                Some(TravelTime(10,113,78,81)),
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
