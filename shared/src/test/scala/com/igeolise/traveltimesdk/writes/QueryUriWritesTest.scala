package com.igeolise.traveltimesdk.writes

import com.igeolise.traveltimesdk.TravelTimeSDK
import com.igeolise.traveltimesdk.dto.common.Coords
import com.igeolise.traveltimesdk.dto.requests.geocoding.{GeocodingAutocompleteRequest, GeocodingRequest, ReverseGeocodingRequest}
import com.softwaremill.sttp.{Uri, _}
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec

class QueryUriWritesTest extends AnyFunSpec with Matchers {
  it("search geocoding queryUri construction") {
    val request: GeocodingRequest = GeocodingRequest("Parliament square", Some(Coords(51.512539, -0.097541)))
    val requestQueryUri: Uri = request.queryUri(TravelTimeSDK.DEFAULT_HOST)
    val expectedUri: Uri = uri"http://api.traveltimeapp.com/v4/geocoding/search?query=Parliament square&focus.lat=51.512539&focus.lng=-0.097541"
    requestQueryUri should equal (expectedUri)
  }

  it("autocomplete geocoding queryUri construction") {
    val request: GeocodingAutocompleteRequest = GeocodingAutocompleteRequest("lon")
    val requestQueryUri: Uri = request.queryUri(TravelTimeSDK.DEFAULT_HOST)
    val expectedUri: Uri = uri"http://api.traveltimeapp.com/v4/geocoding/autocomplete?query=lon"
    requestQueryUri should equal (expectedUri)
  }

  it("reverse geocoding queryUri construction") {
    val request: ReverseGeocodingRequest = ReverseGeocodingRequest(Coords(51.507281, -0.13212))
    val requestQueryUri: Uri = request.queryUri(TravelTimeSDK.DEFAULT_HOST)
    val expectedUri: Uri = uri"http://api.traveltimeapp.com/v4/geocoding/reverse?lat=51.507281&lng=-0.13212"
    requestQueryUri should equal (expectedUri)
  }
}
