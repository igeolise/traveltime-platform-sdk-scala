package com.traveltime.sdk.reads

import com.traveltime.sdk.json.reads.TimeMapReads._
import com.traveltime.sdk.dto.responses.TimeMapResponse
import com.traveltime.sdk.dto.responses.TimeMapResponse._
import com.traveltime.sdk.TestUtils
import com.traveltime.sdk.dto.common.Coords
import com.traveltime.sdk.dto.requests.common.TimeMapProps.{Agency, TimeMapResponseProperties}
import com.traveltime.sdk.dto.responses.TimeMapResponse
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
                  Coords(51.50739324, -0.1284627199999988)
                ),
                Seq()
              )
            ),
            TimeMapResponseProperties(
              Some(false),
              Some(Vector(
                Agency("Kauno miesto administracija", Vector("bus", "train")),
                Agency("Kauno miesto administracija 2", Vector("ferry"))
              ))
            )
          )
        ),
      Json.parse(json)
    )
  }
}