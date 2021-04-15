package com.traveltime.sdk.reads

import com.traveltime.sdk.TestUtils._
import com.traveltime.sdk.dto.responses.common.DistanceBreakdown
import com.traveltime.sdk.dto.responses.common.DistanceBreakdown.BreakdownPart
import com.traveltime.sdk.dto.responses.timefilter.TimeFilterResponse
import com.traveltime.sdk.dto.responses.timefilter.TimeFilterResponse.{Location, Properties, SingleSearchResult}
import com.traveltime.sdk.json.reads.timefilter.TimeFilterReads._
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec
import play.api.libs.json.{JsSuccess, Json}

import scala.concurrent.duration._

class TimeFilterReadsTest extends AnyFunSpec with Matchers {

  it("parse forward_search response") {
    val json = resource("shared/src/test/resources/json/TimeFilter/response/timeFilterResponse.json")
    val result = Json.parse(json).validate[TimeFilterResponse]

    result shouldBe a [JsSuccess[_]]

    result.get shouldBe TimeFilterResponse(
      Seq(
        SingleSearchResult(
          "forward search example",
          Seq(
            Location(
              "Hyde Park",
              Seq(
                Properties(Some(Duration(1753, SECONDS))),
                Properties(Some(Duration(1804, SECONDS))),
                Properties(Some(Duration(1815, SECONDS)))
              )
            )
          ),
          Seq(
            "ZSL London Zoo"
          )
        )
      ),
      Json.parse(json)
    )
  }

  it("parse response with a distance_breakdown parameter set") {
    val jsonSource = resource("shared/src/test/resources/json/TimeFilter/response/timeFilterResponse-distance-breakdown.json")
    val parseResult = Json.parse(jsonSource).validate[TimeFilterResponse]
    val expectedResult = TimeFilterResponse(
      Seq(
        SingleSearchResult(
          "backward search example",
          Seq(
            Location(
              "Hyde Park",
              Seq(
                Properties(
                  None,
                  None,
                  Some( DistanceBreakdown(Seq(BreakdownPart("bus", 1786), BreakdownPart("walk", 1256))) )
                )
              )
            )
          ),
          Seq("ZSL London Zoo")
        ),
        SingleSearchResult(
          "forward search example",
          Seq.empty[Location],
          Seq("Hyde Park", "ZSL London Zoo")
        )
      ),

      Json.parse(jsonSource)
    )

    parseResult shouldBe a [JsSuccess[_]]

    expectedResult shouldBe parseResult.get
  }
}