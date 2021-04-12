package com.example.pokemon_doisdedin

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(com.example.pokemon_doisdedin.services.koin.modules)
        }
    }
}