package com.example.climatecomparer.di

import com.example.climatecomparer.data.database.FavoriteCitiesDatabase
import com.example.climatecomparer.data.remote.GeoLocationAPI
import com.example.climatecomparer.data.remote.WeatherAPI
import com.example.climatecomparer.data.repository.impl.GeoLocationRepositoryImpl
import com.example.climatecomparer.data.repository.impl.WeatherRepositoryImpl
import com.example.climatecomparer.data.repository.repointerface.GeoLocationRepositoryInterface
import com.example.climatecomparer.data.repository.repointerface.WeatherRepositoryInterface
import com.example.climatecomparer.data.repository.impl.FavoriteCitiesRepositoryImpl
import com.example.climatecomparer.data.repository.repointerface.FavoriteCitiesRepositoryInterface
import com.example.climatecomparer.navigation.NavigationViewModel
import com.example.climatecomparer.ui.detailmain.viewmodel.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    //Favorite-Cities Datenbank
    single {
        FavoriteCitiesDatabase.getDatabase(androidContext())
    }

    single {
        get<FavoriteCitiesDatabase>().favoriteDao()
    }

    // API Services
    single {
        WeatherAPI.retrofitService
    }

    single {
        GeoLocationAPI.retrofitService
    }

    // Repositories
    single<WeatherRepositoryInterface> {
        WeatherRepositoryImpl(get())
    }

    single<GeoLocationRepositoryInterface> {
        GeoLocationRepositoryImpl(get())
    }

    single<FavoriteCitiesRepositoryInterface> {
        FavoriteCitiesRepositoryImpl(get())
    }

    // ViewModels
    viewModelOf(::WeatherViewModel)
    viewModelOf(::NavigationViewModel)
}