package com.example.climatecomparer.ui.citylist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.FavoriteCity
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.data.repository.repointerface.WeatherRepositoryInterface
import com.example.climatecomparer.ui.detailmain.screen.getBackgroundImageResource
import com.example.climatecomparer.ui.detailmain.screen.getWeatherIconResource
import org.koin.compose.koinInject
import java.time.LocalDateTime

@Composable
fun FavoriteCityCard(
    favoriteCity: FavoriteCity,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weatherRepository: WeatherRepositoryInterface = koinInject()
    var weather by remember { mutableStateOf<Weather?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Wetter für diese Stadt laden
    LaunchedEffect(favoriteCity.geoLocation) {
        try {
            val city = City(
                geoLocation = favoriteCity.geoLocation,
                isFavorite = true
            )
            weather = weatherRepository.getCurrentWeatherForCity(city)
        } catch (e: Exception) {
            // Fehlerbehandlung - weather bleibt null
        } finally {
            isLoading = false
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Hintergrundbild basierend auf Wetterzustand
            if (weather != null) {
                Image(
                    painter = painterResource(
                        id = getBackgroundImageResource(
                            weather!!.weatherState,
                            weather!!.timeStamp
                        )
                    ),
                    contentDescription = "Weather background",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Fallback-Hintergrund wenn kein Wetter verfügbar
                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {}
            }

            // Overlay für bessere Lesbarkeit
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {}

            // Content
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Stadt-Info und Wetter
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Stadtname
                        Text(
                            text = favoriteCity.geoLocation.locationName ?: "Unbekannte Stadt",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        // Land/Region
                        Text(
                            text = buildString {
                                favoriteCity.geoLocation.state?.let { append("$it, ") }
                                favoriteCity.geoLocation.countryCode?.let { append(it) }
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )

                        if (weather != null) {
                            Spacer(modifier = Modifier.height(8.dp))

                            // Wetterdaten
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Wetter-Icon
                                Image(
                                    painter = painterResource(
                                        id = getWeatherIconResource(
                                            weather!!.weatherState,
                                            weather!!.timeStamp
                                        )
                                    ),
                                    contentDescription = weather!!.weatherState.description,
                                    modifier = Modifier.size(24.dp)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                // Temperatur
                                Text(
                                    text = "${weather!!.temperature.toInt()}°C",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                // Wetterzustand
                                Text(
                                    text = weather!!.weatherState.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.9f),
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    // Action Buttons
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = onToggleFavorite,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Favorit entfernen",
                                tint = Color.Red,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Auswählen",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

// Alternative: Kompakte Version für kleinere Cards
@Composable
fun CompactFavoriteCityCard(
    favoriteCity: FavoriteCity,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weatherRepository: WeatherRepositoryInterface = koinInject()
    var weather by remember { mutableStateOf<Weather?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(favoriteCity.geoLocation) {
        try {
            val city = City(
                geoLocation = favoriteCity.geoLocation,
                isFavorite = true
            )
            weather = weatherRepository.getCurrentWeatherForCity(city)
        } catch (e: Exception) {
            // Fehlerbehandlung
        } finally {
            isLoading = false
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Hintergrundbild
            if (weather != null) {
                Image(
                    painter = painterResource(
                        id = getBackgroundImageResource(
                            weather!!.weatherState,
                            weather!!.timeStamp
                        )
                    ),
                    contentDescription = "Weather background",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {}
            }

            // Overlay
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {}

            // Content
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = favoriteCity.geoLocation.locationName ?: "Unbekannte Stadt",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1
                    )

                    if (weather != null && !isLoading) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(
                                    id = getWeatherIconResource(
                                        weather!!.weatherState,
                                        weather!!.timeStamp
                                    )
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "${weather!!.temperature.toInt()}°",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    } else if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Favorit entfernen",
                            tint = Color.Red,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Auswählen",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}