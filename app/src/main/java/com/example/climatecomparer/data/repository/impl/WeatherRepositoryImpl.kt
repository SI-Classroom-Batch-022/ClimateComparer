package com.example.climatecomparer.data.repository.impl

import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.data.model.WeatherState
import com.example.climatecomparer.data.remote.WeatherAPIService
import com.example.climatecomparer.data.repository.repointerface.WeatherRepositoryInterface
import java.time.LocalDateTime
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.model.HourlyWeather
import com.example.climatecomparer.data.model.HourlyWeatherData
import com.example.climatecomparer.data.remote.GeoLocationAPI
import com.example.climatecomparer.data.remote.WeatherAPI
import kotlinx.coroutines.runBlocking

class WeatherRepositoryImpl(
    private val apiSource: WeatherAPIService
) : WeatherRepositoryInterface {

    override suspend fun getCurrentWeatherForCity(city: City): Weather? {
        return try {
            val lat = city.geoLocation.latitude
            val lon = city.geoLocation.longitude

            // Current weather abrufen
            val response = apiSource.getCurrentWeather(lat, lon)
            val current = response.currentWeather

            // Hourly weather für zusätzliche Daten abrufen
            val hourlyResponse = apiSource.getHourlyWeather(lat, lon)
            val hourlyData = hourlyResponse.hourlyWeather

            // UV-Index und Niederschlag aus den stündlichen Daten extrahieren
            val currentHourIndex = hourlyData.time.indexOfFirst { timeStr ->
                val hourTime = LocalDateTime.parse(timeStr)
                val currentTime = LocalDateTime.parse(current.time)
                hourTime.hour == currentTime.hour && hourTime.toLocalDate() == currentTime.toLocalDate()
            }

            val uvIndex = if (currentHourIndex >= 0) {
                hourlyData.uvIndex[currentHourIndex].toInt()
            } else {
                hourlyData.uvIndex.firstOrNull()?.toInt() ?: 0
            }

            val rainFall = if (currentHourIndex >= 0) {
                hourlyData.rainFall[currentHourIndex]
            } else {
                hourlyData.rainFall.firstOrNull() ?: 0.0
            }

            Weather(
                city = city,
                temperature = current.temperature,
                weatherState = mapWeatherCodeToState(current.weatherCode),
                windSpeed = current.windSpeed,
                windDirection = current.windDirection,
                timeStamp = LocalDateTime.parse(current.time),
                uvIndex = uvIndex,
                rainFall = rainFall
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override suspend fun getHourlyForecastForCity(city: City): List<HourlyWeather>? {
        return try {
            val lat = city.geoLocation.latitude
            val lon = city.geoLocation.longitude
            val hourlyResponse = apiSource.getHourlyWeather(lat, lon)
            val hourly = hourlyResponse.hourlyWeather

            mapHourlyDataToList(hourly)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override suspend fun getDailyForecastForCity(city: City): Weather? {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentWeatherForCoordinates(
        latitude: Double,
        longitude: Double
    ): Weather? {
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


    fun mapHourlyDataToList(hourly: HourlyWeatherData): List<HourlyWeather> {
        return hourly.time.indices.map { weatherNow ->
            HourlyWeather(
                time = hourly.time[weatherNow],
                temperature = hourly.temperature[weatherNow],
                weatherCode = hourly.weatherCode[weatherNow],
                uvIndex = hourly.uvIndex[weatherNow],
                windSpeed = hourly.windSpeed[weatherNow],
                windDirection = hourly.windDirection[weatherNow],
                precipitation = hourly.rainFall[weatherNow]
            )
        }
    }
}




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
            geoLocation = geoLocation.copy(locationName = cityName),
            isFavorite = false
        )

        val weatherRepository = WeatherRepositoryImpl(apiSource = WeatherAPI.retrofitService)

        // Aktuelles Wetter abrufen
        val currentWeather = weatherRepository.getCurrentWeatherForCity(city)

        if (currentWeather != null) {
            println("Aktuelles Wetter in ${city.geoLocation.locationName ?: "Unbekannte Stadt"}:")
            println("  Temperatur: ${currentWeather.temperature}°C")
            println("  Zustand: ${currentWeather.weatherState.description}")
            println("  Wind: ${currentWeather.windSpeed} km/h aus ${currentWeather.windDirection}°")
            println("  Zeitstempel: ${currentWeather.timeStamp}")
        } else {
            println("Fehler beim Abrufen des aktuellen Wetters für ${city.geoLocation.locationName}")
        }

        // Stündliche Vorhersage abrufen
        val hourlyForecast = weatherRepository.getHourlyForecastForCity(city)

        if (!hourlyForecast.isNullOrEmpty()) {
            println("\nStündliche Wettervorhersage:")
            hourlyForecast.take(6).forEach { hour ->
                println("  Zeit: ${hour.time}")
                println("    Temperatur: ${hour.temperature}°C")
                println("    Wettercode: ${hour.weatherCode}")
                println("    UV-Index: ${hour.uvIndex}")
                println("    Wind: ${hour.windSpeed} km/h aus ${hour.windDirection}°")
                println("    Niederschlag: ${hour.precipitation} mm")
            }
        } else {
            println("Keine stündliche Wettervorhersage verfügbar.")
        }

    } catch (e: Exception) {
        println("Fehler bei der Ausführung: ${e.message}")
    }
}
