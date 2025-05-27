package com.example.climatecomparer.data.model

import com.squareup.moshi.Json

data class GeoLocation(
    @Json(name = "name")
    val locationName: String? = null,
    val latitude: Double,
    val longitude: Double,
    @Json(name = "country_code")
    val countryCode: String? = null,
    @Json(name = "admin1")
    val state: String? = null
)