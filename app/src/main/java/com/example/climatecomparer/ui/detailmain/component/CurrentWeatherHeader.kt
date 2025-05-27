package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.data.model.Weather
import java.time.format.DateTimeFormatter

@Composable
fun CurrentWeatherHeader(weather: Weather, modifier: Modifier) {

    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Text(
            text = weather.city.geoLocation.locationName ?: "Unbekannter Ort",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Companion.Medium,
            color = Color.Companion.White
        )

        Spacer(modifier = Modifier.Companion.height(8.dp))

        Text(
            text = "${weather.temperature.toInt()}°C",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Companion.Light,
            color = Color.Companion.White
        )

        Text(
            text = weather.weatherState.description,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Companion.White.copy(alpha = 0.9f)
        )

        Spacer(modifier = Modifier.Companion.height(8.dp))

        Text(
            text = weather.timeStamp.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Companion.White.copy(alpha = 0.8f)
        )
    }

}