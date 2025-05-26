package com.example.climatecomparer.data.model

data class Weather (
    val city: City,
    val temperature: Double,
    val weatherState: WeatherState,
    val uvIndex: Int,
    val windSpeed: Double,
    val windDirection: Double,
    val rainFall: Double,
)

