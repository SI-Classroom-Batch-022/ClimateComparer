package com.example.climatecomparer.navigation

import com.example.climatecomparer.data.model.City

data class NavigationUiState(
    val favoriteCities: List<City> = emptyList(),
    val currentCity: City? = null,
    val isLoading: Boolean = false
)