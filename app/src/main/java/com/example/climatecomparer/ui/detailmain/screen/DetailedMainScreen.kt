package com.example.climatecomparer.ui.detailmain.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.R
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.data.model.WeatherState
import com.example.climatecomparer.ui.detailmain.component.CurrentWeatherHeader
import com.example.climatecomparer.ui.detailmain.component.DailyForecastSection
import com.example.climatecomparer.ui.detailmain.component.ForecastPeriodSwitcher
import com.example.climatecomparer.data.dummyWeather
import com.example.climatecomparer.ui.detailmain.component.HourlyWeatherSection
import com.example.climatecomparer.ui.detailmain.component.WeatherDetailsSection
import com.example.climatecomparer.ui.theme.ClimateComparerTheme
import com.example.climatecomparer.ui.detailmain.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

enum class ForecastPeriod(val displayName: String, val days: Int) {
    SEVEN_DAYS("7 Tage", 7),
    FOURTEEN_DAYS("14 Tage", 14),
    SIXTEEN_DAYS("16 Tage", 16)
}

@Composable
fun DetailedMainScreen(
    weather: Weather,
    hourlyWeather: List<Weather> = emptyList(),
    dailyWeather: List<Weather> = emptyList(),
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = koinViewModel()
) {
    var selectedForecastPeriod by remember { mutableStateOf(ForecastPeriod.SEVEN_DAYS) }

    val uiState by weatherViewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Hintergrundbild basierend auf Wetterzustand
        Image(
            painter = painterResource(id = getBackgroundImageResource(weather.weatherState)),
            contentDescription = "Weather background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding() // Für System UI
        ) {
            // Header außerhalb des Scrollbereichs für bessere Sichtbarkeit
            CurrentWeatherHeader(
                weather = weather,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Scrollbarer Inhalt
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Stündliche Vorhersage
                if (hourlyWeather.isNotEmpty()) {
                    HourlyWeatherSection(hourlyWeather = hourlyWeather)
                }

                // Detaillierte Wetterinformationen
                WeatherDetailsSection(weather = weather)

                // Forecast Period Switcher
                if (dailyWeather.isNotEmpty()) {
                    ForecastPeriodSwitcher(
                        selectedPeriod = selectedForecastPeriod,
                        onPeriodSelected = { selectedForecastPeriod = it }
                    )

                    // Daily Forecast Section
                    DailyForecastSection(
                        dailyWeather = dailyWeather,
                        forecastPeriod = selectedForecastPeriod
                    )
                }

                // Spacer am Ende für Navigation Bar
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// Funktion zur Auswahl des Hintergrundbildes basierend auf WeatherState
fun getBackgroundImageResource(weatherState: WeatherState): Int {
    return when (weatherState) {
        WeatherState.SUNNY -> R.drawable.sunny
        WeatherState.PARTLY_CLOUDY -> R.drawable.partly_cloudy
        WeatherState.CLOUDY -> R.drawable.very_cloudy
        WeatherState.RAINY -> R.drawable.rainy
        WeatherState.STORMY -> R.drawable.thunderstorm
        WeatherState.SNOWY -> R.drawable.very_cloudy // Fallback, da kein snow.png vorhanden
        WeatherState.FOGGY -> R.drawable.foggy
        WeatherState.WINDY -> R.drawable.partly_cloudy // Fallback für windig
    }
}

// Neue Funktion für custom Weather Icons mit Tag/Nacht-Unterscheidung
fun getWeatherIconResource(weatherState: WeatherState, timestamp: LocalDateTime): Int {
    val hour = timestamp.hour
    val isNight = hour < 6 || hour >= 20 // Nacht zwischen 20:00 und 06:00

    return when (weatherState) {
        WeatherState.SUNNY -> {
            if (isNight) R.drawable.night_clear_sky else R.drawable.sun
        }
        WeatherState.PARTLY_CLOUDY -> {
            if (isNight) R.drawable.night_partly_cloudy else R.drawable.day_partly_cloudy
        }
        WeatherState.CLOUDY -> {
            // Für bewölkt verwenden wir das Tag-Icon, da Wolken Tag/Nacht ähnlich aussehen
            R.drawable.day_partly_cloudy
        }
        WeatherState.RAINY -> R.drawable.rain
        WeatherState.STORMY -> R.drawable.thunderstormy
        WeatherState.SNOWY -> R.drawable.snow
        WeatherState.FOGGY -> R.drawable.fog
        WeatherState.WINDY -> R.drawable.windy
    }
}

// Alte Icon-Funktion entfernt, da wir jetzt custom Icons verwenden
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun getWeatherIcon(weatherState: WeatherState): ImageVector {
    // Diese Funktion wird nicht mehr verwendet, aber bleibt für Kompatibilität
    // Falls Sie sie noch irgendwo verwenden
    return when (weatherState) {
        WeatherState.SUNNY -> Icons.Filled.WbSunny
        WeatherState.PARTLY_CLOUDY -> Icons.Default.WbCloudy
        WeatherState.CLOUDY -> Icons.Default.Cloud
        WeatherState.RAINY -> Icons.Default.Umbrella
        WeatherState.STORMY -> Icons.Default.Thunderstorm
        WeatherState.SNOWY -> Icons.Default.AcUnit
        WeatherState.FOGGY -> Icons.Default.CloudQueue
        WeatherState.WINDY -> Icons.Default.Air
    }
}

// Vereinfachte Farbfunktion - keine unterschiedlichen Farben mehr
@Composable
fun getWeatherColor(weatherState: WeatherState): Color {
    // Einheitliche weiße Farbe für alle Icons
    return Color.White
}

@Composable
fun getUVIndexColor(uvIndex: Int): Color {
    return when {
        uvIndex <= 2 -> Color(0xFF4CAF50) // Grün
        uvIndex <= 5 -> Color(0xFFFFEB3B) // Gelb
        uvIndex <= 7 -> Color(0xFFFF9800) // Orange
        uvIndex <= 10 -> Color(0xFFF44336) // Rot
        else -> Color(0xFF9C27B0) // Violett
    }
}

fun getWindDirection(degrees: Double): String {
    return when ((degrees / 45).toInt() % 8) {
        0 -> "N"
        1 -> "NO"
        2 -> "O"
        3 -> "SO"
        4 -> "S"
        5 -> "SW"
        6 -> "W"
        7 -> "NW"
        else -> "N"
    }
}

// Preview-Daten für stündliche Vorhersage
private fun createHourlyWeatherData(): List<Weather> {
    return (1..12).map { hour ->
        dummyWeather.copy(
            temperature = dummyWeather.temperature + (Math.random() * 6 - 3),
            timeStamp = dummyWeather.timeStamp.plusHours(hour.toLong()),
            rainFall = if (hour % 4 == 0) Math.random() * 2 else 0.0,
            weatherState = when (hour % 4) {
                0 -> WeatherState.RAINY
                1 -> WeatherState.PARTLY_CLOUDY
                2 -> WeatherState.SUNNY
                else -> WeatherState.CLOUDY
            }
        )
    }
}

// Preview-Daten für tägliche Vorhersage
private fun createDailyWeatherData(): List<Weather> {
    return (1..16).map { day ->
        dummyWeather.copy(
            temperature = dummyWeather.temperature + (Math.random() * 10 - 5),
            timeStamp = dummyWeather.timeStamp.plusDays(day.toLong()),
            rainFall = if (day % 3 == 0) Math.random() * 5 else 0.0,
            weatherState = when (day % 8) {
                0 -> WeatherState.SUNNY
                1 -> WeatherState.PARTLY_CLOUDY
                2 -> WeatherState.CLOUDY
                3 -> WeatherState.RAINY
                4 -> WeatherState.STORMY
                5 -> WeatherState.SNOWY
                6 -> WeatherState.FOGGY
                else -> WeatherState.WINDY
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailedMainScreenPreview() {
    ClimateComparerTheme {
        DetailedMainScreen(
            weather = dummyWeather,
            hourlyWeather = createHourlyWeatherData(),
            dailyWeather = createDailyWeatherData()
        )
    }
}