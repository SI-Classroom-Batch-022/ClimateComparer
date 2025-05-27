package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Opacity
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
fun DailyWeatherCard(weather: Weather) {
    SmallGlassmorphismCard(
        modifier = Modifier.Companion.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Companion.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Datum und Wochentag
            Column(
                modifier = Modifier.Companion.weight(2f)
            ) {
                Text(
                    text = weather.timeStamp.format(DateTimeFormatter.ofPattern("EEE")),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Companion.Medium,
                    color = Color.Companion.White
                )
                Text(
                    text = weather.timeStamp.format(DateTimeFormatter.ofPattern("dd.MM")),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Companion.White.copy(alpha = 0.8f)
                )
            }

            // Wetter Icon und Beschreibung
            Row(
                modifier = Modifier.Companion.weight(3f),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Icon(
                    imageVector = getWeatherIcon(weather.weatherState),
                    contentDescription = weather.weatherState.description,
                    modifier = Modifier.Companion.size(24.dp),
                    tint = getWeatherColor(weather.weatherState)
                )

                Spacer(modifier = Modifier.Companion.width(8.dp))

                Text(
                    text = weather.weatherState.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Companion.White.copy(alpha = 0.9f),
                    maxLines = 1
                )
            }

            // Temperatur und Niederschlag
            Column(
                modifier = Modifier.Companion.weight(2f),
                horizontalAlignment = Alignment.Companion.End
            ) {
                Text(
                    text = "${weather.temperature.toInt()}°C",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Companion.Bold,
                    color = Color.Companion.White
                )

                if (weather.rainFall > 0) {
                    Row(
                        verticalAlignment = Alignment.Companion.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Opacity,
                            contentDescription = "Niederschlag",
                            modifier = Modifier.Companion.size(12.dp),
                            tint = Color.Companion.Blue
                        )
                        Spacer(modifier = Modifier.Companion.width(2.dp))
                        Text(
                            text = "${weather.rainFall.toInt()}mm",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Companion.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}