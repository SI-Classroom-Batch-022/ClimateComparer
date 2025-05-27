package com.example.climatecomparer.data.repository.impl

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.data.model.WeatherState
import com.example.climatecomparer.data.remote.WeatherAPIService
import com.example.climatecomparer.data.repository.repointerface.WeatherRepositoryInterface
import java.time.LocalDateTime
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.remote.GeoLocationAPI
import com.example.climatecomparer.data.remote.WeatherAPI
import kotlinx.coroutines.runBlocking

class WeatherRepositoryImpl(
    private val apiSource: WeatherAPIService
) : WeatherRepositoryInterface {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCurrentWeatherForCity(city: City): Weather? {
        return try {
            val lat = city.geoLocation.latitude
            val lon = city.geoLocation.longitude
            val response = apiSource.getCurrentWeather(lat, lon)
            val current = response.currentWeather

            Weather(
                city = city,
                temperature = current.temperature,
                weatherState = mapWeatherCodeToState(current.weatherCode),
                uvIndex = current.uvIndex.toInt(),
                windSpeed = current.windSpeed,
                windDirection = current.windDirection,
                rainFall = current.precipitation,
                timeStamp = LocalDateTime.parse(current.time)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getCurrentWeatherForCoordinates(latitude: Double, longitude: Double): Weather? {
        TODO("Not yet implemented")
    }

    private fun mapWeatherCodeToState(code: Int): WeatherState {
        return when (code) {
            0 -> WeatherState.SUNNY
            in 1..3 -> WeatherState.PARTLY_CLOUDY
            in 45..48 -> WeatherState.FOGGY
            in 51..67 -> WeatherState.RAINY
            in 71..77 -> WeatherState.SNOWY
            in 80..82 -> WeatherState.RAINY
            in 95..99 -> WeatherState.STORMY
            else -> WeatherState.CLOUDY
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun main() = runBlocking {
    val cityName = "München"

    try {

        val geoResponse = GeoLocationAPI.retrofitService.searchCityByName(cityName)
        val geoLocation: GeoLocation? = geoResponse.results?.firstOrNull()

        if (geoLocation == null) {
            println("Keine Geodaten gefunden für '$cityName'")
            return@runBlocking
        }


        val city = City(
            geoLocation = geoLocation,
            isFavorite = false
        )


        val weatherRepository = WeatherRepositoryImpl(apiSource = WeatherAPI.retrofitService)
        val weather = weatherRepository.getCurrentWeatherForCity(city)

        if (weather != null) {
            println("Wetter in ${city.geoLocation.locationName ?: "Unbekannte Stadt"}:")
            println("  Temperatur: ${weather.temperature}°C")
            println("  Zustand: ${weather.weatherState.description}")
            println("  UV-Index: ${weather.uvIndex}")
            println("  Wind: ${weather.windSpeed} km/h aus ${weather.windDirection}°")
            println("  Niederschlag: ${weather.rainFall} mm")
            println("  Zeitstempel: ${weather.timeStamp}")
        } else {
            println("Fehler beim Abrufen des Wetters für ${city.geoLocation.locationName}")
        }
    } catch (e: Exception) {
        println("Fehler bei der Ausführung: ${e.message}")
    }
}