package com.traveltime.sdk.writes

import com.traveltime.sdk.{TravelTimeHost, TravelTimeSDK}
import com.traveltime.sdk.dto.common.Coords
import com.traveltime.sdk.dto.requests.geocoding.{GeocodingAutocompleteRequest, GeocodingRequest, ReverseGeocodingRequest}
import com.sun.jndi.toolkit.url.Uri
import com.traveltime.sdk.TravelTimeHost
import com.traveltime.sdk.dto.requests.geocoding.GeocodingAutocompleteRequest
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec
import sttp.client.UriContext
import sttp.model

class QueryUriWritesTest extends AnyFunSpec with Matchers {
  it("search geocoding queryUri construction") {
    val request: GeocodingRequest = GeocodingRequest("Parliament square", Some(Coords(51.512539, -0.097541)))
    val requestQueryUri = request.sttpRequest(TravelTimeHost.defaultHost).uri
    val expectedUri: model.Uri = uri"https://api.traveltimeapp.com/v4/geocoding/search?query=Parliament square&focus.lat=51.512539&focus.lng=-0.097541"
    requestQueryUri should equal (expectedUri)
  }

  it("autocomplete geocoding queryUri construction") {
    val request: GeocodingAutocompleteRequest = GeocodingAutocompleteRequest("lon")
    val requestQueryUri = request.sttpRequest(TravelTimeHost.defaultHost).uri
    val expectedUri: model.Uri= uri"https://api.traveltimeapp.com/v4/geocoding/autocomplete?query=lon"
    requestQueryUri should equal (expectedUri)
  }

  it("reverse geocoding queryUri construction") {
    val request: ReverseGeocodingRequest = ReverseGeocodingRequest(Coords(51.507281, -0.13212))
    val requestQueryUri = request.sttpRequest(TravelTimeHost.defaultHost).uri
    val expectedUri: model.Uri = uri"https://api.traveltimeapp.com/v4/geocoding/reverse?lat=51.507281&lng=-0.13212"
    requestQueryUri should equal (expectedUri)
  }
}
