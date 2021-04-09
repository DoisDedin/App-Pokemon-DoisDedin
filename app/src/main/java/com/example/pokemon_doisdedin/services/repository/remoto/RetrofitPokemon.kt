package com.example.pokemon_doisdedin.services.repository.remoto

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitPokemon {
    companion object {
        private lateinit var retrofit: Retrofit
        private val baseUrl = "https://pokeapi.co/api/v2/"
        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (!Companion::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
        fun <T> createService(serviceClass: Class<T>): T{
            return getRetrofitInstance().create(serviceClass)
        }
    }
}