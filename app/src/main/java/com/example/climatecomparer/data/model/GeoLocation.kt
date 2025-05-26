package com.example.climatecomparer.data.model

import com.squareup.moshi.Json

data class GeoLocation(
    val name: String? = null,
    val latitude: Double,
    val longitude: Double,
    @Json(name = "postcode")
    val postcode: String? = null,
    val country: String? = null,
    @Json(name = "admin1")
    val state: String? = null
)