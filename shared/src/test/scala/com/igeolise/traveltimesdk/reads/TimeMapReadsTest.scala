package com.igeolise.traveltimesdk.reads

import com.igeolise.traveltimesdk.json.reads.TimeMapReads._
import com.igeolise.traveltimesdk.dto.responses.TimeMapResponse
import com.igeolise.traveltimesdk.dto.responses.TimeMapResponse._
import com.igeolise.traveltimesdk.TestUtils
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.common.CommonProperties.TimeMapProps.TimeMapResponseProperties
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.{JsSuccess, Json}

class TimeMapReadsTest extends AnyFunSpec with Matchers {

  it("parse departure_searches response") {
    val json = TestUtils.resource("shared/src/test/resources/json/TimeMap/response/timeMapResponse-NoJoints.json")
    val result = Json.parse(json).validate[TimeMapResponse]

    result shouldBe a [JsSuccess[_]]

    result.get shouldBe TimeMapResponse(
        Seq(
          SingleSearchResult(
            "public transport from Trafalgar Square",
            Seq(
              Shape(
                Seq(
                  Coords(51.50739324, -0.1284627199999988),
                  Coords(51.50784288, -0.12891235999999887),
                  Coords(51.50829252, -0.1284627199999988),
                  Coords(51.50784288, -0.12801307999999875),
                  Coords(51.50739324, -0.1284627199999988),
                ),
                Seq()
              )
            ),
            TimeMapResponseProperties(None)
          )
        ),
      Json.parse(json)
    )
  }
}