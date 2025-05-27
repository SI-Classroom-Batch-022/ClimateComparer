package com.example.climatecomparer.di

import com.example.climatecomparer.data.remote.GeoLocationAPI
import com.example.climatecomparer.data.remote.WeatherAPI
import com.example.climatecomparer.data.repository.impl.GeoLocationRepositoryImpl
import com.example.climatecomparer.data.repository.impl.WeatherRepositoryImpl
import com.example.climatecomparer.data.repository.repointerface.GeoLocationRepositoryInterface
import com.example.climatecomparer.data.repository.repointerface.WeatherRepositoryInterface
import com.example.climatecomparer.ui.detailmain.viewmodel.WeatherViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single {
        WeatherAPI.retrofitService
    }

    single {
        GeoLocationAPI.retrofitService
    }

    single<WeatherRepositoryInterface> {
        WeatherRepositoryImpl(get())
    }

    single<GeoLocationRepositoryInterface> {
        GeoLocationRepositoryImpl(get())
    }

    viewModelOf(::WeatherViewModel)
}