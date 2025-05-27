package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.data.model.Weather

@Composable
fun HourlyWeatherSection(hourlyWeather: List<Weather>) {

    Column {
        Text(
            text = "Stündliche Vorhersage",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Companion.Medium,
            color = Color.Companion.White,
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(hourlyWeather) { weather ->
                HourlyWeatherCard(weather = weather)
            }
        }

        Spacer(modifier = Modifier.Companion.height(8.dp))
    }

}