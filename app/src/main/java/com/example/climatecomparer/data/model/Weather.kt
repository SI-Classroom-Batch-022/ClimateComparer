package com.example.climatecomparer.data.model

import java.time.LocalDateTime

data class Weather (
    val city: City,
    val temperature: Double,
    val weatherState: WeatherState,
    val windSpeed: Double,
    val windDirection: Double,
    val timeStamp: LocalDateTime,
    val uvIndex: Int,
    val rainFall: Double
)