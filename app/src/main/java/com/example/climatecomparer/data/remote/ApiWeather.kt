package com.example.climatecomparer.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val WEATHER_URL = "https://api.open-meteo.com/v1/forecast"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(WEATHER_URL)
    .build()

interface APIService {

    @GET("current")
    suspend fun getCurrentWeather(

    )
}

object MealsAPI {
    val retrofitService: GeoLocationAPIService by lazy { retrofit.create(GeoLocationAPIService::class.java) }
}