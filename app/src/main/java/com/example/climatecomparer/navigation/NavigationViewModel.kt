package com.example.climatecomparer.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.repository.repointerface.FavoritesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val favoritesRepository: FavoritesRepositoryInterface
) : ViewModel() {

    private val _uiState = MutableStateFlow(NavigationUiState())
    val uiState: StateFlow<NavigationUiState> = _uiState.asStateFlow()

    init {
        loadFavorites()
        setDefaultCity()
    }

    private fun setDefaultCity() {
        val defaultCity = City(
            geoLocation = GeoLocation(
                locationName = "Berlin",
                latitude = 52.52,
                longitude = 13.405,
                countryCode = "DE",
            ),
            isFavorite = true
        )
        _uiState.value = _uiState.value.copy(currentCity = defaultCity)
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val favorites = favoritesRepository.getFavoriteCities()
                _uiState.value = _uiState.value.copy(
                    favoriteCities = favorites,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun selectCity(city: City) {
        _uiState.value = _uiState.value.copy(currentCity = city)
    }

    fun toggleFavorite(city: City) {
        viewModelScope.launch {
            try {
                if (city.isFavorite) {
                    favoritesRepository.removeFavoriteCity(city.toString())
                } else {
                    favoritesRepository.addFavoriteCity(city.copy(isFavorite = true).toString())
                }
                loadFavorites()
            } catch (e: Exception) {
                // Fehlerbehandlung
            }
        }
    }
}