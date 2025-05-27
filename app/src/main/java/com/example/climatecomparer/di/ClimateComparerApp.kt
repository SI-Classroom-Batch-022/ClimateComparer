package com.example.climatecomparer.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ClimateComparerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ClimateComparerApp)
            modules(appModule)
        }
    }
}