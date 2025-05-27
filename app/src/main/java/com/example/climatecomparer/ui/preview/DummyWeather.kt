package com.example.climatecomparer.ui.preview

import com.example.climatecomparer.data.model.City
import com.example.climatecomparer.data.model.GeoLocation
import com.example.climatecomparer.data.model.Weather
import com.example.climatecomparer.data.model.WeatherState
import java.time.LocalDateTime

val dummyWeather = Weather(
    city = City(
        geoLocation = GeoLocation(
            locationName = "Berlin",
            latitude = 52.52,
            longitude = 13.405,
            countryCode = "DE",
            state = "Berlin"
        ),
        isFavorite = true
    ),
    temperature = 21.5,
    weatherState = WeatherState.PARTLY_CLOUDY,
    uvIndex = 5,
    rainFall = 0.0,
    windSpeed = 10.0,
    windDirection = 135.0,
    timeStamp = LocalDateTime.of(2023, 1, 1, 12, 0)
)