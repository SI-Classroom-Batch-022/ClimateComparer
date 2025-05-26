package com.example.climatecomparer.data.model

import com.squareup.moshi.Json


data class GeoLocation(

    val cityName: String,

    val latitude: Double,
    val longitude: Double,

    @Json(name = "country_code")
    val countryCode: String,

    @Json(name = "postcodes")
    val postCodes: Int
)