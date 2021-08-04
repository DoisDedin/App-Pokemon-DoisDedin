package com.example.pokemon_doisdedin.services.repository.remoto

import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import kotlinx.coroutines.flow.Flow


interface PokemonService {
    fun getPokemonsApi(): Flow<ArrayList<PokemonResultModel>>
}