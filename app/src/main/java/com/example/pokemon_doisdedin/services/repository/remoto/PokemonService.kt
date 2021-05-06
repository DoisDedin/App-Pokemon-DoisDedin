package com.example.pokemon_doisdedin.services.repository.remoto

import com.example.pokemon_doisdedin.services.repository.local.room.entity.PokemonResultModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon/{id}/")
  suspend fun pokemon(@Path("id") id: String): Response<PokemonResultModel>

}