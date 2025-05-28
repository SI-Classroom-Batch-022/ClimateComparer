package com.example.climatecomparer.data.repository.impl

import com.example.climatecomparer.data.database.FavoriteCitiesDao
import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.FavoriteCity
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.model.GeoLocationConverter
import com.example.climatecomparer.data.repository.repointerface.FavoriteCitiesRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class FavoriteCitiesRepositoryImpl(
    private val favoriteCitiesDao: FavoriteCitiesDao
): FavoriteCitiesRepositoryInterface {

    private val geoLocationConverter = GeoLocationConverter()

    override val favoriteCities: Flow<List<FavoriteCity>> = favoriteCitiesDao.getAllFavoriteCities()

    override suspend fun addFavoriteCity(newFavoriteCity: FavoriteCity) {
        favoriteCitiesDao.insert(newFavoriteCity)
    }

    override suspend fun removeFavoriteCity(favoriteCity: FavoriteCity) {
        favoriteCitiesDao.delete(favoriteCity)
    }

    override suspend fun getFavoriteCities(): List<City> {
        return favoriteCities.first().map { favoriteCity ->
            City(
                geoLocation = favoriteCity.geoLocation,
                isFavorite = true
            )
        }
    }

    override suspend fun isCityFavorite(city: City): Boolean {
        val geoLocationJson = geoLocationConverter.fromGeoLocation(city.geoLocation)
        return favoriteCitiesDao.findByGeoLocation(geoLocationJson) != null
    }

    override suspend fun toggleFavorite(city: City) {
        val geoLocationJson = geoLocationConverter.fromGeoLocation(city.geoLocation)
        val existingFavorite = favoriteCitiesDao.findByGeoLocation(geoLocationJson)

        if (existingFavorite != null) {
            // Remove from favorites
            favoriteCitiesDao.delete(existingFavorite)
        } else {
            // Add to favorites
            val favoriteCity = FavoriteCity(geoLocation = city.geoLocation)
            favoriteCitiesDao.insert(favoriteCity)
        }
    }
}