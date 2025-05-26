package com.example.climatecomparer.data.repository.repointerface

import com.example.climatecomparer.data.model.GeoLocation

interface GeoLocationRepositoryInterface {

    suspend fun getGeoLocationForCity(cityName: String): GeoLocation

}