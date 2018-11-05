package com.igeolise.traveltimesdk.reads

import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterPostcodesResponse
import com.igeolise.traveltimesdk.dto.responses.timefilter.TimeFilterPostcodesResponse.{Postcode, PostcodesProperties, SingleSearchResult}
import com.igeolise.traveltimesdk.json.reads.timefilter.TimeFilterPostcodesReads._
import org.scalatest.{FunSpec, Matchers}
import play.api.libs.json.{JsSuccess, Json}
import scala.concurrent.duration.Duration
import scala.concurrent.duration.SECONDS

class TimeFilterPostcodesReadsTest extends FunSpec with Matchers {

  it("parse departurePostcodesResponse response") {
    val json =
      TestUtils.resource("shared/src/test/resources/json/TimeFilterPostcodes/response/departurePostcodesResponse.json")
    val result = Json.parse(json).validate[TimeFilterPostcodesResponse]

    result shouldBe a [JsSuccess[_]]

    result.get shouldBe TimeFilterPostcodesResponse(
      Seq(
        SingleSearchResult(
          "public transport from Trafalgar Square",
          Seq(
            Postcode(
              "WC2N 5DS",
              Seq(
                PostcodesProperties(
                  Some(Duration(67, SECONDS)),
                  Some(51)
                )
              )
            ),
            Postcode(
              "WC2N 5DU",
              Seq(
                PostcodesProperties(
                  Some(Duration(51, SECONDS)),
                  Some(40)
                )
              )
            )
          )
        )
      ),
      Json.parse(json)
    )
  }


}
