package com.igeolise.traveltimesdk.dto.requests.geocoding

abstract class GeocodingResourceType(val endpoint: String)
object Reverse extends GeocodingResourceType("reverse")
object Search extends GeocodingResourceType("search")
object Autocomplete extends GeocodingResourceType("autocomplete")