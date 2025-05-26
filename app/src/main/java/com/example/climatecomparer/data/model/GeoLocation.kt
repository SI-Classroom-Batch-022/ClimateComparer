package com.example.climatecomparer.data.model

import com.squareup.moshi.Json

data class GeoLocation(
    val name: String? = null,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null,
    @Json(name = "admin1")
    val state: String? = null
)