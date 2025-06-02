package com.example.climatecomparer.ui.citylist.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.ui.citylist.component.FavoriteCityCard
import com.example.climatecomparer.ui.citylist.component.CompactFavoriteCityCard
import com.example.climatecomparer.ui.citylist.component.SearchResultCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListScreen(
    favoritesCities: List<City>,
    onCitySelected: (City) -> Unit,
    onBackPressed: () -> Unit,
    onSearchCity: suspend (String) -> List<GeoLocation>,
    onToggleFavorite: (City) -> Unit,
    modifier: Modifier = Modifier,
    useCompactCards: Boolean = false // Optional: für kompakte Card-Variante
) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<GeoLocation>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Städte") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Suchfeld
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    if (query.length >= 2) {
                        isSearching = true
                    }
                },
                label = { Text("Stadt suchen") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            searchResults = emptyList()
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "Löschen")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // Suche durchführen
            LaunchedEffect(searchQuery) {
                if (searchQuery.length >= 2) {
                    delay(500) // Debounce
                    searchResults = onSearchCity(searchQuery)
                    isSearching = false
                } else {
                    searchResults = emptyList()
                }
            }

            if (isSearching) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp) // Erhöhter Spacing für die größeren Cards
            ) {
                // Suchergebnisse
                if (searchResults.isNotEmpty()) {
                    item {
                        Text(
                            "Suchergebnisse",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(searchResults) { geoLocation ->
                        // Prüfen ob diese Stadt bereits in Favoriten ist
                        val isFavorite = favoritesCities.any { city ->
                            city.geoLocation.latitude == geoLocation.latitude &&
                                    city.geoLocation.longitude == geoLocation.longitude
                        }

                        SearchResultCard(
                            geoLocation = geoLocation,
                            isFavorite = isFavorite,
                            onClick = {
                                val city = City(
                                    geoLocation = geoLocation,
                                    isFavorite = isFavorite
                                )
                                onCitySelected(city)
                            },
                            onToggleFavorite = {
                                val city = City(
                                    geoLocation = geoLocation,
                                    isFavorite = isFavorite
                                )
                                onToggleFavorite(city)
                            }
                        )
                    }

                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }

                // Favoriten
                if (favoritesCities.isNotEmpty()) {
                    item {
                        Text(
                            "Favoriten",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(favoritesCities) { city ->
                        // Convert City to FavoriteCity for the card
                        val favoriteCity = com.example.climatecomparer.data.model.FavoriteCity(
                            geoLocation = city.geoLocation
                        )

                        if (useCompactCards) {
                            // Kompakte Version verwenden
                            CompactFavoriteCityCard(
                                favoriteCity = favoriteCity,
                                onClick = { onCitySelected(city) },
                                onToggleFavorite = { onToggleFavorite(city) }
                            )
                        } else {
                            // Standard-Version mit vollem Wetter-Hintergrund verwenden
                            FavoriteCityCard(
                                favoriteCity = favoriteCity,
                                onClick = { onCitySelected(city) },
                                onToggleFavorite = { onToggleFavorite(city) }
                            )
                        }
                    }
                } else if (searchResults.isEmpty() && searchQuery.isEmpty()) {
                    item {
                        Text(
                            "Keine Favoriten vorhanden",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}