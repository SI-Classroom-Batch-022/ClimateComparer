package com.example.climatecomparer.data.repository.impl

import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.repository.repointerface.FavoritesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class FavoritesRepositoryImpl: FavoritesRepositoryInterface {
    // In-Memory Storage (sp#ter durch Room Database ersetzen)
    private val _favorites = MutableStateFlow<List<City>>(
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
            ),
            City(
                geoLocation = GeoLocation(
                    locationName = "Hamburg",
                    latitude = 53.55,
                    longitude = 10.00,
                    countryCode = "DE",
                ),
                isFavorite = true
            )
        )
    )

    override suspend fun getFavoriteCities(): List<City> {
        return _favorites.first()
    }

    override suspend fun addFavoriteCity(city: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavoriteCity(city: String) {
        TODO("Not yet implemented")
    }

}