package com.example.pokeapi_koin.common

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokeAPIApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokeAPIApplication)
            modules(appModule)
        }
    }
}