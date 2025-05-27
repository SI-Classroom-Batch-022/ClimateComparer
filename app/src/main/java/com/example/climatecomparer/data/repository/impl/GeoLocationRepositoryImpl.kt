package com.example.climatecomparer.data.repository.impl

import android.util.Log
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.remote.GeoLocationAPIService
import com.example.climatecomparer.data.repository.repointerface.GeoLocationRepositoryInterface

abstract class GeoLocationRepositoryImpl(
    val apiSource: GeoLocationAPIService
): GeoLocationRepositoryInterface {

    override suspend fun getGeoLocationForCity(cityName: String): GeoLocation {
        return try {
            val response = apiSource.searchCityByName(cityName)
            response.results?.firstOrNull()
                ?: GeoLocation(
                    latitude = 0.0,
                    longitude = 0.0,
                    locationName = null,
                    countryCode = null,
                    state = null
                )
        } catch (e: Exception) {
            Log.e("GeoRepo", "Fehler bei Geolocation: ${e.message}")
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

