package com.example.climatecomparer.data.model

import com.squareup.moshi.Json

data class WeatherResponseCurrent(
    val latitude: Double,
    val longitude: Double,
    @Json(name = "current_weather")
    val currentWeather: CurrentWeatherData

)

data class CurrentWeatherData(
    val time: String,
    @Json(name = "temperature")
    val temperature: Double,
    @Json(name = "weathercode")
    val weatherCode: Int,
    @Json(name = "windspeed")
    val windSpeed: Double,
    @Json(name = "winddirection")
    val windDirection: Double
)

data class WeatherResponseHourly(
    val latitude: Double,
    val longitude: Double,
    @Json(name = "hourly")
    val hourlyWeather: HourlyWeatherData,
    @Json(name = "hourly_units")
    val hourlyUnits: WeatherUnits
)

data class HourlyWeatherData(
    val time: List<String>,
    @Json(name = "temperature_2m")
    val temperature: List<Double>,
    @Json(name = "weathercode")
    val weatherCode: List<Int>,
    @Json(name = "uv_index")
    val uvIndex: List<Double>,
    @Json(name = "wind_speed_10m")
    val windSpeed: List<Double>,
    @Json(name = "wind_direction_10m")
    val windDirection: List<Double>,
    @Json(name = "precipitation")
    val rainFall: List<Double>
)

data class WeatherUnits(
    val time: String?,
    @Json(name = "temperature_2m")
    val temperature: String?,
    @Json(name = "weathercode")
    val weatherCode: String?,
    @Json(name = "uv_index")
    val uvIndex: String?,
    @Json(name = "wind_speed_10m")
    val windSpeed: String?,
    @Json(name = "wind_direction_10m")
    val windDirection: String?,
    @Json(name = "precipitation")
    val rainFall: String?
)