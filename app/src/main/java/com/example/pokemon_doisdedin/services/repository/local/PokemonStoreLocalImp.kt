package com.example.pokemon_doisdedin.services.repository.local

import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.services.repository.local.datastore.UserSettingsStore
import com.example.pokemon_doisdedin.services.repository.local.room.dao.PokemonDao
import java.util.*

class PokemonStoreLocalImp(
    private val userSettingsStore: UserSettingsStore,
    private val pokemonDao: PokemonDao
) : PokemonStoreLocal {
    //dataStore
    override suspend fun readTime(): Long {
        TODO("Not yet implemented")
    }

    override suspend fun storeTime(time: Long) {
        TODO("Not yet implemented")
    }

    //Room
    override suspend fun addListPokemon(entities: List<PokemonResultModel>) {
        TODO("Not yet implemented")
    }

    override fun getAll(): ArrayList<PokemonResultModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllPokemons() {
        TODO("Not yet implemented")
    }

    override fun getpokemon(id: String): PokemonResultModel {
        TODO("Not yet implemented")
    }

}