package com.example.pokemon_doisdedin.services.listener

interface APIListener<T> {
    fun onSuccess(mode: T)

    fun onFailure(str: String)

}