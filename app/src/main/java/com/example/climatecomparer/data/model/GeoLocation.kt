package com.example.climatecomparer.data.model

import com.squareup.moshi.Json

data class GeoLocation(
    val name: String,
    val country: String,
    @Json(name = "admin1")
    val state: String,
    val latitude: Double,
    val longitude: Double
)