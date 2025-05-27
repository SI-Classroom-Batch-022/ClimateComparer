package com.example.climatecomparer.data.model

import com.squareup.moshi.Json

data class GeoLocation(
    val locationName: String? = null,
    val latitude: Double,
    val longitude: Double,
    val countryCode: String? = null,
    @Json(name = "admin1")
    val state: String? = null
)