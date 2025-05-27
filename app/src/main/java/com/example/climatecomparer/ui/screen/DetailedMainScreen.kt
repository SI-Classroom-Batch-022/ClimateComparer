package com.example.climatecomparer.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.R
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.data.model.WeatherState
import com.example.climatecomparer.ui.preview.dummyWeather
import com.example.climatecomparer.ui.theme.ClimateComparerTheme
import com.example.climatecomparer.ui.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

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

        // Inhalt über dem Hintergrundbild
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Aktuelles Wetter Header
            uiState.currentWeather?.let {
                CurrentWeatherHeader(weather = weather)
            }

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
        }
    }
}

@Composable
private fun ForecastPeriodSwitcher(
    selectedPeriod: ForecastPeriod,
    onPeriodSelected: (ForecastPeriod) -> Unit
) {
    Column {
        Text(
            text = "Vorhersage",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ForecastPeriod.values().forEach { period ->
                FilterChip(
                    onClick = { onPeriodSelected(period) },
                    label = {
                        Text(
                            text = period.displayName,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    selected = selectedPeriod == period,
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.White.copy(alpha = 0.2f),
                        labelColor = Color.White,
                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun DailyForecastSection(
    dailyWeather: List<Weather>,
    forecastPeriod: ForecastPeriod
) {
    val filteredWeather = dailyWeather.take(forecastPeriod.days)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filteredWeather.forEach { weather ->
            DailyWeatherCard(weather = weather)
        }
    }
}

@Composable
private fun DailyWeatherCard(weather: Weather) {
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
                Icon(
                    imageVector = getWeatherIcon(weather.weatherState),
                    contentDescription = weather.weatherState.description,
                    modifier = Modifier.size(24.dp),
                    tint = getWeatherColor(weather.weatherState)
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

// Glassmorphism Card Composable
@Composable
private fun GlassmorphismCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.25f),
                        Color.White.copy(alpha = 0.15f)
                    )
                )
            )
    ) {
        // Blur-Hintergrund-Effekt
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Color.White.copy(alpha = 0.1f),
                    RoundedCornerShape(16.dp)
                )
        )

        // Inhalt
        Box(modifier = Modifier.padding(1.dp)) {
            content()
        }
    }
}

// Kleinere Glassmorphism Card für Details
@Composable
private fun SmallGlassmorphismCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.3f),
                        Color.White.copy(alpha = 0.2f)
                    )
                )
            )
    ) {
        // Blur-Hintergrund-Effekt
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Color.White.copy(alpha = 0.15f),
                    RoundedCornerShape(12.dp)
                )
        )

        // Inhalt
        Box(modifier = Modifier.padding(1.dp)) {
            content()
        }
    }
}

// Funktion zur Auswahl des Hintergrundbildes basierend auf WeatherState
private fun getBackgroundImageResource(weatherState: WeatherState): Int {
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

@Composable
private fun CurrentWeatherHeader(weather: Weather) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = weather.city.geoLocation.locationName ?: "Unbekannter Ort",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${weather.temperature.toInt()}°C",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Light,
            color = Color.White
        )

        Text(
            text = weather.weatherState.description,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White.copy(alpha = 0.9f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = weather.timeStamp.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.8f)
        )
    }

}

@Composable
private fun HourlyWeatherSection(hourlyWeather: List<Weather>) {

    Column {
        Text(
            text = "Stündliche Vorhersage",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = Color.White,
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(hourlyWeather) { weather ->
                HourlyWeatherCard(weather = weather)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }

}

@Composable
private fun HourlyWeatherCard(weather: Weather) {
    SmallGlassmorphismCard(
        modifier = Modifier.width(80.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = weather.timeStamp.format(DateTimeFormatter.ofPattern("HH:mm")),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Icon(
                imageVector = getWeatherIcon(weather.weatherState),
                contentDescription = weather.weatherState.description,
                modifier = Modifier.size(24.dp),
                tint = getWeatherColor(weather.weatherState)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${weather.temperature.toInt()}°",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun WeatherDetailsSection(weather: Weather) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Wetterdetails",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        // Erste Reihe: Wind und UV-Index
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherDetailCard(
                title = "Windgeschwindigkeit",
                value = "${weather.windSpeed} km/h",
                icon = Icons.Default.Air,
                modifier = Modifier.weight(1f)
            )

            WeatherDetailCard(
                title = "UV-Index",
                value = weather.uvIndex.toString(),
                icon = Icons.Default.WbSunny,
                valueColor = getUVIndexColor(weather.uvIndex),
                modifier = Modifier.weight(1f)
            )
        }

        // Zweite Reihe: Windrichtung und Niederschlag
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherDetailCard(
                title = "Windrichtung",
                value = "${weather.windDirection.toInt()}°",
                subtitle = getWindDirection(weather.windDirection),
                icon = Icons.Default.Navigation,
                modifier = Modifier.weight(1f)
            )

            WeatherDetailCard(
                title = "Niederschlag",
                value = "${weather.rainFall} mm",
                icon = Icons.Default.Opacity,
                valueColor = if (weather.rainFall > 0) Color.Blue else Color.White,
                modifier = Modifier.weight(1f)
            )
        }

        // Wetterzustand Detail-Karte
        WeatherStateDetailCard(weatherState = weather.weatherState)
    }
}

@Composable
private fun WeatherDetailCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    valueColor: Color = Color.White
) {
    SmallGlassmorphismCard(
        modifier = modifier
            .height(120.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.8f),
                maxLines = 1
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = valueColor,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun WeatherStateDetailCard(weatherState: WeatherState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getWeatherColor(weatherState).copy(alpha = 0.35f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getWeatherIcon(weatherState),
                contentDescription = weatherState.description,
                modifier = Modifier.size(32.dp),
                tint = getWeatherColor(weatherState)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Wetterzustand",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Text(
                    text = weatherState.description,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

// Hilfsfunktionen für Icons und Farben
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun getWeatherIcon(weatherState: WeatherState): ImageVector {
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

@Composable
private fun getWeatherColor(weatherState: WeatherState): Color {
    return when (weatherState) {
        WeatherState.SUNNY -> Color(0xFFFFB74D)
        WeatherState.PARTLY_CLOUDY -> Color(0xFF1D6EB3)
        WeatherState.CLOUDY -> Color(0xFF282727)
        WeatherState.RAINY -> Color(0xFF42A5F5)
        WeatherState.STORMY -> Color(0xFF5C6BC0)
        WeatherState.SNOWY -> Color(0xFF9C27B0)
        WeatherState.FOGGY -> Color(0xFF90A4AE)
        WeatherState.WINDY -> Color(0xFF26C6DA)
    }
}

@Composable
private fun getUVIndexColor(uvIndex: Int): Color {
    return when {
        uvIndex <= 2 -> Color(0xFF4CAF50) // Grün
        uvIndex <= 5 -> Color(0xFFFFEB3B) // Gelb
        uvIndex <= 7 -> Color(0xFFFF9800) // Orange
        uvIndex <= 10 -> Color(0xFFF44336) // Rot
        else -> Color(0xFF9C27B0) // Violett
    }
}

private fun getWindDirection(degrees: Double): String {
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