package com.example.climatecomparer.ui.helper

import com.example.climatecomparer.data.model.HourlyWeather
import com.example.climatecomparer.data.model.Weather

data class WeatherUiState(
    val isLoading: Boolean = false,
    val currentWeather: Weather? = null,
    val hourlyForecast: List<HourlyWeather> = emptyList(),
    val error: String? = null
)