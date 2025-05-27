package com.example.climatecomparer.data.repository.repointerface

import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.HourlyWeather
import com.example.climatecomparer.data.model.Weather

interface WeatherRepositoryInterface{

    suspend fun getCurrentWeatherForCoordinates(latitude: Double, longitude: Double): Weather?

    suspend fun getCurrentWeatherForCity(city: City): Weather?

    suspend fun getHourlyForecastForCity(city: City): List<HourlyWeather>?

    suspend fun getDailyForecastForCity(city: City): Weather?
}