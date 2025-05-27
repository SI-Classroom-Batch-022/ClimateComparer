package com.example.climatecomparer.data.repository.impl

import android.util.Log
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.remote.GeoLocationAPIService
import com.example.climatecomparer.data.repository.repointerface.GeoLocationRepositoryInterface

class GeoLocationRepositoryImpl(
    private val apiSource: GeoLocationAPIService // Add 'private' keyword for proper encapsulation
): GeoLocationRepositoryInterface {

    override suspend fun getGeoLocationForCity(cityName: String): GeoLocation {
        return try {
            Log.d("GeoRepo", "Searching for city: $cityName") // Add debug logging
            val response = apiSource.searchCityByName(cityName)
            Log.d("GeoRepo", "API response received: ${response.results?.size} results")

            response.results?.firstOrNull()
                ?: run {
                    Log.w("GeoRepo", "No results found for city: $cityName")
                    GeoLocation(
                        latitude = 0.0,
                        longitude = 0.0,
                        locationName = null,
                        countryCode = null,
                        state = null
                    )
                }
        } catch (e: Exception) {
            Log.e("GeoRepo", "Error fetching geolocation for $cityName: ${e.message}", e)
            GeoLocation(
                latitude = 0.0,
                longitude = 0.0,
                locationName = null,
                countryCode = null,
                state = null
            )
        }
    }
}

