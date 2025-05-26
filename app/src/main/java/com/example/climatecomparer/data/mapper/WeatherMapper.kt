package com.example.climatecomparer.data.mapper

import com.example.climatecomparer.data.model.*

object WeatherMapper {

    fun mapWeatherCodeToState(weatherCode: Int): WeatherState {
        return when (weatherCode) {
            0 -> WeatherState.SUNNY
            1, 2, 3 -> WeatherState.PARTLY_CLOUDY
            45, 48 -> WeatherState.FOGGY
            51, 53, 55 -> WeatherState.RAINY
            56, 57 -> WeatherState.RAINY
            61, 63, 65 -> WeatherState.RAINY
            66, 67 -> WeatherState.RAINY
            71, 73, 75 -> WeatherState.SNOWY
            77 -> WeatherState.SNOWY
            80, 81, 82 -> WeatherState.RAINY
            85, 86 -> WeatherState.SNOWY
            95 -> WeatherState.STORMY
            96, 99 -> WeatherState.STORMY
            else -> WeatherState.CLOUDY
        }
    }

    fun mapApiResponseToWeather(
        weatherResponse: WeatherResponse,
        city: City
    ): Weather {
        val currentData = weatherResponse.currentWeather


        return Weather(
            city = city,
            temperature = currentData.temperature,
            weatherState = mapWeatherCodeToState(currentData.weatherCode),
            uvIndex = currentData.uvIndex.toInt(), // Convert Double to Int
            windSpeed = currentData.windSpeed,
            windDirection = currentData.windDirection,
            rainFall = currentData.precipitation
        )
    }
}