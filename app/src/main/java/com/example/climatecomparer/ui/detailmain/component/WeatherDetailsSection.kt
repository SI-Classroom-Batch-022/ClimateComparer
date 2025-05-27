package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.ui.detailmain.screen.getUVIndexColor
import com.example.climatecomparer.ui.detailmain.screen.getWindDirection

@Composable
fun WeatherDetailsSection(weather: Weather) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Wetterdetails",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Companion.Medium,
            color = Color.Companion.White
        )

        // Erste Reihe: Wind und UV-Index
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherDetailCard(
                title = "Windgeschwindigkeit",
                value = "${weather.windSpeed} km/h",
                icon = Icons.Default.Air,
                modifier = Modifier.Companion.weight(1f)
            )

            WeatherDetailCard(
                title = "UV-Index",
                value = weather.uvIndex.toString(),
                icon = Icons.Default.WbSunny,
                valueColor = getUVIndexColor(weather.uvIndex),
                modifier = Modifier.Companion.weight(1f)
            )
        }

        // Zweite Reihe: Windrichtung und Niederschlag
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherDetailCard(
                title = "Windrichtung",
                value = "${weather.windDirection.toInt()}°",
                subtitle = getWindDirection(weather.windDirection),
                icon = Icons.Default.Navigation,
                modifier = Modifier.Companion.weight(1f)
            )

            WeatherDetailCard(
                title = "Niederschlag",
                value = "${weather.rainFall} mm",
                icon = Icons.Default.Opacity,
                valueColor = if (weather.rainFall > 0) Color.Companion.Blue else Color.Companion.White,
                modifier = Modifier.Companion.weight(1f)
            )
        }

        // Wetterzustand Detail-Karte
        WeatherStateDetailCard(weatherState = weather.weatherState)
    }
}