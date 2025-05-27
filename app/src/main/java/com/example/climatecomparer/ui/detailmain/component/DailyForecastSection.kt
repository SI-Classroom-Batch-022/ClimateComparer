package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.ui.detailmain.screen.ForecastPeriod

@Composable
fun DailyForecastSection(
    dailyWeather: List<Weather>,
    forecastPeriod: ForecastPeriod
) {
    val filteredWeather = dailyWeather.take(forecastPeriod.days)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filteredWeather.forEach { weather ->
            DailyWeatherCard(weather = weather)
        }
    }
}