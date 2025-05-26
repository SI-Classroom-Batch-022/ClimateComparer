package com.example.climatecomparer.data.repository.repointerface

import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.GeoLocation

interface GeoLocationRepositoryInterface {

    suspend fun getGeoLocationForCity(cityName: String): GeoLocation

    suspend fun fetchCitiesByName(cityName: String): List<City>

}