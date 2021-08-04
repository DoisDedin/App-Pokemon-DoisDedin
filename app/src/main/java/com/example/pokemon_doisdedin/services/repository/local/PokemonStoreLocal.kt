package com.example.pokemon_doisdedin.services.repository.local

import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import kotlinx.coroutines.flow.Flow

interface PokemonStoreLocal {
    //dataStore
    suspend fun readTime() : Long
    suspend fun storeTime(time : Long)
    //ROOM
    suspend fun addListPokemon(entities:List<PokemonResultModel>)
    fun getAll(): ArrayList<PokemonResultModel>
    suspend fun deleteAllPokemons()
    fun getpokemon(id: String): PokemonResultModel
}