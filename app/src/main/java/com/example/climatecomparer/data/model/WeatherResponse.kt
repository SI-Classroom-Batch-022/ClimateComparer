package com.example.climatecomparer.data.model

import com.squareup.moshi.Json

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    @Json(name = "current")
    val currentWeather: CurrentWeatherData,
    @Json(name = "current_units")
    val units: WeatherUnits
)

data class CurrentWeatherData(
    val time: String,
    @Json(name = "temperature_2m")
    val temperature: Double,
    @Json(name = "weather_code")
    val weatherCode: Int,
    @Json(name = "uv_index")
    val uvIndex: Double,
    @Json(name = "wind_speed_10m")
    val windSpeed: Double,
    @Json(name = "wind_direction_10m")
    val windDirection: Double,
    val precipitation: Double
)

data class WeatherUnits(
    val time: String,
    @Json(name = "temperature_2m")
    val temperature: String,
    @Json(name = "weather_code")
    val weatherCode: String,
    @Json(name = "uv_index")
    val uvIndex: String,
    @Json(name = "wind_speed_10m")
    val windSpeed: String,
    @Json(name = "wind_direction_10m")
    val windDirection: String,
    val precipitation: String
)