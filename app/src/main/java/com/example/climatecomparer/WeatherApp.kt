package com.example.climatecomparer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.climatecomparer.data.model.City
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.climatecomparer.data.model.HourlyWeather
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.data.model.WeatherState
import com.example.climatecomparer.ui.detailmain.screen.DetailedMainScreen
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.take

@Composable
fun WeatherScreen(
    uiState: com.example.climatecomparer.ui.helper.WeatherUiState,
    currentCity: City,
    onLoadWeather: (City) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(currentCity) {
        onLoadWeather(currentCity)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.error != null -> {
                Text(text = "Fehler: ${uiState.error}")
            }
            uiState.currentWeather != null -> {
                val hourlyWeatherList = convertHourlyToWeatherList(
                    uiState.hourlyForecast,
                    uiState.currentWeather!!.city
                )

                val dailyWeatherList = createDailyFromHourly(
                    uiState.hourlyForecast,
                    uiState.currentWeather!!.city
                )

                DetailedMainScreen(
                    weather = uiState.currentWeather!!,
                    hourlyWeather = hourlyWeatherList,
                    dailyWeather = dailyWeatherList
                )
            }
            else -> {
                Text(text = "Keine Wetterdaten verfügbar")
            }
        }
    }
}

// Hilfsfunktionen bleiben gleich
private fun convertHourlyToWeatherList(
    hourlyForecast: List<HourlyWeather>,
    city: City
): List<Weather> {
    val now = LocalDateTime.now()

    return hourlyForecast
        .filter { hourly ->
            try {
                val hourlyTime = LocalDateTime.parse(hourly.time)
                hourlyTime.isAfter(now) || hourlyTime.isEqual(now)
            } catch (e: Exception) {
                try {
                    val hourlyTime = LocalDateTime.parse(hourly.time, DateTimeFormatter.ISO_DATE_TIME)
                    hourlyTime.isAfter(now) || hourlyTime.isEqual(now)
                } catch (e2: Exception) {
                    false
                }
            }
        }
        .take(24)
        .map { hourly ->
            Weather(
                city = city,
                temperature = hourly.temperature,
                weatherState = mapWeatherCodeToState(hourly.weatherCode),
                windSpeed = hourly.windSpeed,
                windDirection = hourly.windDirection,
                timeStamp = try {
                    LocalDateTime.parse(hourly.time)
                } catch (e: Exception) {
                    LocalDateTime.parse(hourly.time, DateTimeFormatter.ISO_DATE_TIME)
                },
                uvIndex = hourly.uvIndex.toInt(),
                rainFall = hourly.precipitation
            )
        }
}

private fun createDailyFromHourly(
    hourlyForecast: List<HourlyWeather>,
    city: City
): List<Weather> {
    val dailyGroups = hourlyForecast.groupBy { hourly ->
        try {
            LocalDateTime.parse(hourly.time).toLocalDate()
        } catch (e: Exception) {
            LocalDateTime.parse(hourly.time, DateTimeFormatter.ISO_DATE_TIME).toLocalDate()
        }
    }

    return dailyGroups.map { (date, hourlyData) ->
        val avgTemp = hourlyData.map { it.temperature }.average()
        val maxPrecipitation = hourlyData.maxOf { it.precipitation }
        val avgWindSpeed = hourlyData.map { it.windSpeed }.average()
        val avgWindDirection = hourlyData.map { it.windDirection }.average()
        val maxUvIndex = hourlyData.maxOf { it.uvIndex }

        val mostFrequentWeatherCode = hourlyData
            .groupingBy { it.weatherCode }
            .eachCount()
            .maxByOrNull { it.value }?.key ?: 0

        Weather(
            city = city,
            temperature = avgTemp,
            weatherState = mapWeatherCodeToState(mostFrequentWeatherCode),
            windSpeed = avgWindSpeed,
            windDirection = avgWindDirection,
            timeStamp = date.atStartOfDay(),
            uvIndex = maxUvIndex.toInt(),
            rainFall = maxPrecipitation
        )
    }.sortedBy { it.timeStamp }
}

private fun mapWeatherCodeToState(code: Int): WeatherState {
    return when (code) {
        0 -> WeatherState.SUNNY
        in 1..3 -> WeatherState.PARTLY_CLOUDY
        in 45..48 -> WeatherState.FOGGY
        in 51..67 -> WeatherState.RAINY
        in 71..77 -> WeatherState.SNOWY
        in 80..82 -> WeatherState.RAINY
        in 95..99 -> WeatherState.STORMY
        else -> WeatherState.CLOUDY
    }
}