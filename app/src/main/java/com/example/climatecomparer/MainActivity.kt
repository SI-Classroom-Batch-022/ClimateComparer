package com.example.climatecomparer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.remote.GeoLocationAPI
import com.example.climatecomparer.ui.screen.CityListScreen
import com.example.climatecomparer.ui.theme.ClimateComparerTheme
import com.example.climatecomparer.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClimateComparerTheme {
                AppNavigation()
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: WeatherViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    // Favoriten-Städte (später aus Datenbank laden)
    var favoriteCities by remember {
        mutableStateOf(
            listOf(
                City(
                    geoLocation = GeoLocation(
                        locationName = "Berlin",
                        latitude = 52.52,
                        longitude = 13.405,
                        countryCode = "DE",
                        state = "Berlin"
                    ),
                    isFavorite = true
                )
            )
        )
    }

    var currentCity by remember { mutableStateOf(favoriteCities.first()) }

    NavHost(
        navController = navController,
        startDestination = "weather"
    ) {
        composable("weather") {
            Box(modifier = Modifier.fillMaxSize()) {
                WeatherScreen(
                    uiState = uiState,
                    currentCity = currentCity,
                    onLoadWeather = { city ->
                        currentCity = city
                        viewModel.loadWeather(city)
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // FAB für Städteliste
                FloatingActionButton(
                    onClick = { navController.navigate("cities") },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .systemBarsPadding()
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.LocationCity, contentDescription = "Städte")
                }
            }
        }

        composable("cities") {
            CityListScreen(
                favoritesCities = favoriteCities,
                onCitySelected = { city ->
                    scope.launch {
                        viewModel.loadWeather(city)
                        currentCity = city
                        navController.popBackStack()
                    }
                },
                onBackPressed = { navController.popBackStack() },
                onSearchCity = { query ->
                    try {
                        val response = GeoLocationAPI.retrofitService.searchCityByName(query)
                        response.results ?: emptyList()
                    } catch (e: Exception) {
                        emptyList()
                    }
                },
                onToggleFavorite = { city ->
                    favoriteCities = if (city.isFavorite) {
                        favoriteCities.filter { it.geoLocation.locationName != city.geoLocation.locationName }
                    } else {
                        favoriteCities + city.copy(isFavorite = true)
                    }
                }
            )
        }
    }
}
