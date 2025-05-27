package com.example.climatecomparer.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.climatecomparer.WeatherScreen
import com.example.climatecomparer.ui.citylist.screen.CityListScreen
import com.example.climatecomparer.ui.detailmain.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val weatherViewModel: WeatherViewModel = koinViewModel()
    val navigationViewModel: NavigationViewModel = koinViewModel()
    val scope = rememberCoroutineScope()

    val weatherUiState by weatherViewModel.uiState.collectAsState()
    val navigationUiState by navigationViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "weather"
    ) {
        composable("weather") {
            Box(modifier = Modifier.fillMaxSize()) {
                navigationUiState.currentCity?.let { currentCity ->
                    WeatherScreen(
                        uiState = weatherUiState,
                        currentCity = currentCity,
                        onLoadWeather = { city ->
                            navigationViewModel.selectCity(city)
                            weatherViewModel.loadWeather(city)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

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
                favoritesCities = navigationUiState.favoriteCities,
                onCitySelected = { city ->
                    scope.launch {
                        navigationViewModel.selectCity(city)
                        weatherViewModel.loadWeather(city)
                        navController.popBackStack()
                    }
                },
                onBackPressed = { navController.popBackStack() },
                onSearchCity = { query ->
                    try {
                        val response = com.example.climatecomparer.data.remote.GeoLocationAPI.retrofitService.searchCityByName(query)
                        response.results ?: emptyList()
                    } catch (e: Exception) {
                        emptyList()
                    }
                },
                onToggleFavorite = { city ->
                    navigationViewModel.toggleFavorite(city)
                }
            )
        }
    }
}