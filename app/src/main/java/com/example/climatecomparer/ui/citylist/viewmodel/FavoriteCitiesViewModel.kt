package com.example.climatecomparer.ui.citylist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climatecomparer.data.model.FavoriteCity
import com.example.climatecomparer.data.repository.repointerface.FavoriteCitiesRepositoryInterface
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteCitiesViewModel(
    private val favoriteCitiesRepository: FavoriteCitiesRepositoryInterface
): ViewModel() {

    val favoritesCities = favoriteCitiesRepository.favoriteCities.stateIn(
        initialValue = listOf(),
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed()
    )

    fun removeFavoriteCity(favoriteCity: FavoriteCity) {
        viewModelScope.launch {
            try {
                favoriteCitiesRepository.removeFavoriteCity(favoriteCity)
            } catch(e: Exception) {
                Log.e("ERROR", "Error while trying to remove favorite city ${e.localizedMessage}")
            }
        }
    }

}