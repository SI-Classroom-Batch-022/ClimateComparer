package com.example.climatecomparer.ui.detailmain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.repository.repointerface.WeatherRepositoryInterface
import com.example.climatecomparer.ui.helper.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepositoryInterface
): ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun loadWeather(city: City) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val currentWeather = repository.getCurrentWeatherForCity(city)
                val hourly = repository.getHourlyForecastForCity(city)

                if (currentWeather != null && hourly != null) {

                    _uiState.update {
                        it.copy(
                            currentWeather = currentWeather,
                            hourlyForecast = hourly,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Weather data unavailable.") }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Unknown error")
                }
            }
        }
    }
}