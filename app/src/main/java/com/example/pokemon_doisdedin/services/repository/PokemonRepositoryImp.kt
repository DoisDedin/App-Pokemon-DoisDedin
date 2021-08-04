package com.example.pokemon_doisdedin.services.repository

import androidx.lifecycle.MutableLiveData
import com.example.pokemon_doisdedin.services.auxiliares.ValidationTime
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.services.repository.local.PokemonStoreLocal
import com.example.pokemon_doisdedin.services.repository.remoto.PokemonService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImp(
    private val pokemonService: PokemonService,
    private val pokemonStoreLocal: PokemonStoreLocal,
    private val validationTime: ValidationTime
) : PokemonRepository {


    var mWhereData = MutableLiveData<Int>()

    override suspend fun getPokemons(): Flow<ArrayList<PokemonResultModel>> {
        val currentTime = System.currentTimeMillis()
        mWhereData.postValue(validationTime.cacheIsValid(currentTime))
        return flow {
            when (mWhereData.value) {
                2 -> {
                    pokemonStoreLocal.getAll()
                }
                else -> {
                    pokemonStoreLocal.deleteAllPokemons()
                    pokemonService.getPokemonsApi()
                }
            }
        }
    }


}