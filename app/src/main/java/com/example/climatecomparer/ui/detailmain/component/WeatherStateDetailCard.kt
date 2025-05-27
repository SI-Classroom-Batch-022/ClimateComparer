package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.data.model.WeatherState
import com.example.climatecomparer.ui.detailmain.screen.getWeatherColor
import com.example.climatecomparer.ui.detailmain.screen.getWeatherIcon

@Composable
fun WeatherStateDetailCard(weatherState: WeatherState) {
    Card(
        modifier = Modifier.Companion.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getWeatherColor(weatherState).copy(alpha = 0.35f)
        )
    ) {
        Row(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Icon(
                imageVector = getWeatherIcon(weatherState),
                contentDescription = weatherState.description,
                modifier = Modifier.Companion.size(32.dp),
                tint = getWeatherColor(weatherState)
            )

            Spacer(modifier = Modifier.Companion.width(16.dp))

            Column {
                Text(
                    text = "Wetterzustand",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Companion.White.copy(alpha = 0.8f)
                )
                Text(
                    text = weatherState.description,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Companion.Medium,
                    color = Color.Companion.White
                )
            }
        }
    }
}