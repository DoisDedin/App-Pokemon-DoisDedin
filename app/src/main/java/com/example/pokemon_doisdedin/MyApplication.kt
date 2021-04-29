package com.example.pokemon_doisdedin


import com.example.pokemon_doisdedin.services.koin.myModule.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(provideDependency())
        }
    }

    open fun provideDependency() = appComponent
}