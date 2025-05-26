package com.example.climatecomparer.data.remote

import com.example.climatecomparer.data.model.GeoResponse
import com.example.climatecomparer.data.repository.impl.GeoLocationRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val GEOLOCATION_URL = "https://geocoding-api.open-meteo.com/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(GEOLOCATION_URL)
    .build()

interface GeoLocationAPIService {

    @GET("search")
    suspend fun searchCityByName(
        @Query("name") cityName: String,
        @Query("count") count: Int = 10,
        @Query("language") language: String = "de"
    ): GeoResponse

}

object GeoLocationAPI {
    val retrofitService: GeoLocationAPIService by lazy {
        retrofit.create(GeoLocationAPIService::class.java)
    }
}

private fun main() = runBlocking {
    val repo = GeoLocationRepositoryImpl(GeoLocationAPI.retrofitService)
    val cities = repo.fetchCitiesByName("Berlin")

    cities.forEach {
        println("Stadt: ${it.geoLocation.name}, Wo?: ${it.geoLocation.country}, ${it.geoLocation.state}, Lat: ${it.geoLocation.latitude}, Lon: ${it.geoLocation.longitude}")
    }
}