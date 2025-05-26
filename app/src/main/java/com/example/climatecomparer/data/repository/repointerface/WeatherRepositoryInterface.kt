package com.example.climatecomparer.data.repository.repointerface

import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.Weather

interface WeatherRepositoryInterface{

    suspend fun getCurrentWeatherForCoordinates(latitude: Double, longitude: Double): Weather?

    suspend fun getCurrentWeatherForCity(city: City): Weather?
}