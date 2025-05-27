package com.example.climatecomparer.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.ui.preview.dummyWeather

@Composable
fun CurrentLocationWeather(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = weather.city.geoLocation.locationName ?: "Unbekannter Ort",
            style = MaterialTheme.typography.titleMedium
            )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${weather.temperature.toInt()}°C",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = weather.weatherState.description,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentLocationWeatherPreview() {
    CurrentLocationWeather(dummyWeather)
}
