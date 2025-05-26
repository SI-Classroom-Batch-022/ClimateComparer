package com.example.climatecomparer.data.model

import java.time.LocalDateTime

data class Weather (
    val city: City,
    val temperature: Double,
    val weatherState: WeatherState,
    val uvIndex: Int,
    val windSpeed: Double,
    val windDirection: Double,
    val rainFall: Double,
    val timeStamp: LocalDateTime
)

