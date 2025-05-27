package com.example.climatecomparer.data.repository.repointerface

import com.example.climatecomparer.data.model.City

interface FavoritesRepositoryInterface {
    suspend fun getFavoriteCities(): List<City>
    suspend fun addFavoriteCity(city: String)
    suspend fun removeFavoriteCity(city: String)
}