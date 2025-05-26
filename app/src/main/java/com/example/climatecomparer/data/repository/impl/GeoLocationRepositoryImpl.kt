package com.example.climatecomparer.data.repository.impl

import android.util.Log
import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.remote.GeoLocationAPIService
import com.example.climatecomparer.data.repository.repointerface.GeoLocationRepositoryInterface

class GeoLocationRepositoryImpl(
    private val apiSource: GeoLocationAPIService
): GeoLocationRepositoryInterface {

    override suspend fun getGeoLocationForCity(cityName: String): GeoLocation {
        return try {
            val response = apiSource.searchCityByName(cityName)
            response.results?.firstOrNull()
                ?: GeoLocation(
                    latitude = 0.0, longitude = 0.0,
                    name = "",
                    state = "",
                    country = ""
                )
        } catch (e: Exception) {
            Log.e("GeoRepo", "Fehler bei Geolocation: ${e.message}")
            GeoLocation(
                latitude = 0.0, longitude = 0.0,
                name = "",
                state = "",
                country = ""
            )
        }
    }

    override suspend fun fetchCitiesByName(cityName: String): List<City> {
        return try {
            val response = apiSource.searchCityByName(cityName)
            val results = response.results ?: emptyList()

            results.map { location ->
                City(
                    geoLocation = GeoLocation(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        name = location.name,
                        state = location.state,
                        country = location.country,
                    ),
                    isFavorite = false,
                )
            }
        } catch (e: Exception) {
            Log.e("GeoRepo", "Fehler beim Abrufen von Städten: ${e.message}")
            emptyList()
        }
    }
}

