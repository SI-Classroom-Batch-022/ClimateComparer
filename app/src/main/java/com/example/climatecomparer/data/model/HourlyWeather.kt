package com.example.climatecomparer.data.model

data class HourlyWeather (
    val time: String,
    val temperature: Double,
    val weatherCode: Int,
    val uvIndex: Double,
    val windSpeed: Double,
    val windDirection: Double,
    val precipitation: Double
)