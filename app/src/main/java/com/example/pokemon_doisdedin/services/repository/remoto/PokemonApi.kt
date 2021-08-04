package com.example.pokemon_doisdedin.services.repository.remoto

import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("pokemon/{id}/")
  suspend fun pokemon(@Path("id") id: String): PokemonResultModel
}