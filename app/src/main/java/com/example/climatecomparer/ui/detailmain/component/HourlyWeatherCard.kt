package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.ui.detailmain.screen.getWeatherColor
import com.example.climatecomparer.ui.detailmain.screen.getWeatherIcon
import java.time.format.DateTimeFormatter

@Composable
fun HourlyWeatherCard(weather: Weather) {
    SmallGlassmorphismCard(
        modifier = Modifier.Companion.width(80.dp)
    ) {
        Column(
            modifier = Modifier.Companion
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = weather.timeStamp.format(DateTimeFormatter.ofPattern("HH:mm")),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Companion.Medium,
                color = Color.Companion.White
            )

            Spacer(modifier = Modifier.Companion.height(8.dp))

            Icon(
                imageVector = getWeatherIcon(weather.weatherState),
                contentDescription = weather.weatherState.description,
                modifier = Modifier.Companion.size(24.dp),
                tint = getWeatherColor(weather.weatherState)
            )

            Spacer(modifier = Modifier.Companion.height(8.dp))

            Text(
                text = "${weather.temperature.toInt()}°",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Companion.Medium,
                color = Color.Companion.White
            )
        }
    }
}