package com.example.pokemon_doisdedin.services.repository

import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemons() : Flow<ArrayList<PokemonResultModel>>
}