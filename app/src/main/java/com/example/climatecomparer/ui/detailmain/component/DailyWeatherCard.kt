package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.ui.detailmain.screen.getWeatherIconResource
import java.time.format.DateTimeFormatter

@Composable
fun DailyWeatherCard(weather: Weather) {
    SmallGlassmorphismCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Datum und Wochentag
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = weather.timeStamp.format(DateTimeFormatter.ofPattern("EEE")),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    text = weather.timeStamp.format(DateTimeFormatter.ofPattern("dd.MM")),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            // Wetter Icon und Beschreibung
            Row(
                modifier = Modifier.weight(3f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = getWeatherIconResource(weather.weatherState, weather.timeStamp)),
                    contentDescription = weather.weatherState.description,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = weather.weatherState.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f),
                    maxLines = 1
                )
            }

            // Temperatur und Niederschlag
            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${weather.temperature.toInt()}°C",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                if (weather.rainFall > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Opacity,
                            contentDescription = "Niederschlag",
                            modifier = Modifier.size(12.dp),
                            tint = Color.Blue
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${weather.rainFall.toInt()}mm",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}