package com.example.climatecomparer.data.repository.repointerface

import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.FavoriteCity
import kotlinx.coroutines.flow.Flow

interface FavoriteCitiesRepositoryInterface {

    val favoriteCities: Flow<List<FavoriteCity>>

    suspend fun addFavoriteCity(newFavoriteCity: FavoriteCity)

    suspend fun removeFavoriteCity(favoriteCity: FavoriteCity)

}