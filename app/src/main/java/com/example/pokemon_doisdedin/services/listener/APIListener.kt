package com.example.pokemon_doisdedin.services.listener

interface APIListener<T> {
    suspend fun onSuccess(mode: T)

    suspend fun onFailure(str: String)

}