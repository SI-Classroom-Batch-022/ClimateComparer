package com.example.climatecomparer.data.repository.impl

import com.example.climatecomparer.data.database.FavoriteCitiesDao
import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.FavoriteCity
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.repository.repointerface.FavoriteCitiesRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class FavoriteCitiesRepositoryImpl(
    private val favoriteCitiesDao: FavoriteCitiesDao
): FavoriteCitiesRepositoryInterface {

    override val favoriteCities: Flow<List<FavoriteCity>> = favoriteCitiesDao.getAllFavoriteCities()

    override suspend fun addFavoriteCity(newFavoriteCity: FavoriteCity) {
        favoriteCitiesDao.insert(newFavoriteCity)
    }

    override suspend fun removeFavoriteCity(favoriteCity: FavoriteCity) {
        favoriteCitiesDao.delete(favoriteCity)
    }

}