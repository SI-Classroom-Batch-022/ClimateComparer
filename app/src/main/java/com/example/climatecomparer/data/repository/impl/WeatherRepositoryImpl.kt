package com.example.climatecomparer.data.repository.impl

import android.util.Log
import com.example.climatecomparer.data.mapper.WeatherMapper
import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.data.remote.WeatherAPIService
import com.example.climatecomparer.data.repository.repointerface.WeatherRepositoryInterface

class WeatherRepositoryImpl(
    private val apiService: WeatherAPIService
) : WeatherRepositoryInterface {

    override suspend fun getCurrentWeatherForCity(city: City): Weather? {
        return try {
            val latitude = city.geoLocation.latitude
            val longitude = city.geoLocation.longitude

            Log.d("WeatherRepo", "Fetching weather for coordinates: $latitude, $longitude")

            val weatherResponse = apiService.getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            )

            val weather = WeatherMapper.mapApiResponseToWeather(weatherResponse, city)

            Log.d("WeatherRepo", "Successfully fetched weather: ${weather.temperature}°C, ${weather.weatherState}")

            weather

        } catch (e: Exception) {
            Log.e("WeatherRepo", "Error fetching weather data: ${e.message}", e)
            null
        }
    }

    override suspend fun getCurrentWeatherForCoordinates(
        latitude: Double,
        longitude: Double
    ): Weather? {
        return try {
            val weatherResponse = apiService.getCurrentWeather(latitude, longitude)

            // Create a temporary city object for the weather data
            // In a real app, you might want to reverse geocode to get city name
            val tempCity = City(
                geoLocation = com.example.climatecomparer.data.model.GeoLocation(
                    name = "Unknown Location",
                    latitude = latitude,
                    longitude = longitude,
                    country = null,
                    state = null
                ),
                isFavorite = false
            )

            WeatherMapper.mapApiResponseToWeather(weatherResponse, tempCity)

        } catch (e: Exception) {
            Log.e("WeatherRepo", "Error fetching weather data for coordinates: ${e.message}", e)
            null
        }
    }
}



