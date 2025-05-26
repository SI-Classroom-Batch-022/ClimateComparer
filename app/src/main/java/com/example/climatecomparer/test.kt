package com.example.climatecomparer

import com.example.climatecomparer.data.mapper.WeatherMapper
import com.example.climatecomparer.data.model.*
import com.example.climatecomparer.data.remote.*
import kotlinx.coroutines.runBlocking

/**
 * Diese Testklasse demonstriert den vollständigen Datenfluss:
 * 1. Stadtname → Koordinaten (GeoLocation API)
 * 2. Koordinaten → Wetterdaten (Weather API)
 * 3. API Response → Domain Model (Mapping)
 */
suspend fun main() = runBlocking {
    println("🌍 Starte kompletten API-Test für Climate Comparer")
    println("=" .repeat(50))

    // Die Stadt, die wir testen wollen
    val testCity = "München"
    println("📍 Teste mit Stadt: $testCity")
    println()

    // === SCHRITT 1: GeoLocation API testen ===
    println("🔍 SCHRITT 1: Hole Koordinaten für $testCity")
    println("-".repeat(30))

    try {
        val geoAPI = GeoLocationAPI.retrofitService
        val geoResponse = geoAPI.searchCityByName(testCity)

        if (geoResponse.results?.isNotEmpty() == true) {
            val location = geoResponse.results.first()

            println("✅ GeoLocation API erfolgreich!")
            println("   Name: ${location.name}")
            println("   Land: ${location.country}")
            println("   Bundesland: ${location.state}")
            println("   Koordinaten: ${location.latitude}, ${location.longitude}")
            println()

            // === SCHRITT 2: Weather API mit den erhaltenen Koordinaten testen ===
            println("🌤️  SCHRITT 2: Hole Wetterdaten für diese Koordinaten")
            println("-".repeat(40))

            val weatherAPI = WeatherAPI.retrofitService
            val weatherResponse = weatherAPI.getCurrentWeather(
                latitude = location.latitude,
                longitude = location.longitude
            )

            println("✅ Weather API erfolgreich!")
            println("   Koordinaten verwendet: ${weatherResponse.latitude}, ${weatherResponse.longitude}")
            println("   Zeitstempel: ${weatherResponse.currentWeather.time}")
            println("   Temperatur: ${weatherResponse.currentWeather.temperature}°C")
            println("   Wetter-Code: ${weatherResponse.currentWeather.weatherCode}")
            println("   UV-Index: ${weatherResponse.currentWeather.uvIndex}")
            println("   Windgeschwindigkeit: ${weatherResponse.currentWeather.windSpeed} km/h")
            println("   Windrichtung: ${weatherResponse.currentWeather.windDirection}°")
            println("   Niederschlag: ${weatherResponse.currentWeather.precipitation} mm")
            println()

            // Erstelle ein City-Objekt aus der GeoLocation
            val city = City(
                geoLocation = location,
                isFavorite = false
            )

            // Verwende den Mapper, um die Weather API Response zu konvertieren
            val weather = WeatherMapper.mapApiResponseToWeather(weatherResponse, city)

            println("✅ Mapping erfolgreich!")
            println("   Stadt: ${weather.city.geoLocation.name}")
            println("   Temperatur: ${weather.temperature}°C")
            println("   Wetterzustand: ${weather.weatherState.description}")
            println("   UV-Index: ${weather.uvIndex}")
            println("   Windgeschwindigkeit: ${weather.windSpeed} km/h")
            println("   Windrichtung: ${weather.windDirection}°")
            println("   Regenfall: ${weather.rainFall} mm")
            println()

            // === SCHRITT 4: Repository-Pattern testen ===
            println("🏛️ SCHRITT 4: Teste Repository-Pattern")
            println("-".repeat(35))


        } else {
            println("❌ Keine Ergebnisse für Stadt '$testCity' gefunden")
        }

    } catch (e: Exception) {
        println("❌ Fehler beim API-Test:")
        println("   Fehlermeldung: ${e.message}")
        println("   Fehlertyp: ${e.javaClass.simpleName}")
        e.printStackTrace()
    }

    println()
    println("🎯 Test abgeschlossen!")
    println("=" .repeat(50))
}
