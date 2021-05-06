package com.example.pokemon_doisdedin.viewmodel


import android.app.Application
import androidx.lifecycle.*
import com.example.pokemon_doisdedin.services.auxiliares.ValidationTime

import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.services.listener.APIListener
import com.example.pokemon_doisdedin.services.repository.local.room.entity.PokemonResultModel
import com.example.pokemon_doisdedin.services.repository.PokemonRepository
import com.example.pokemon_doisdedin.services.repository.local.datastore.DataStoreRepositoryLocal
import com.example.pokemon_doisdedin.services.repository.local.room.dao.PokemonsDataBase
import kotlinx.coroutines.*

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


class MainActivityViewModel(
    var application: Application,
    var dataBase: PokemonsDataBase,
    var dataStore: DataStoreRepositoryLocal,
    var validation: ValidationTime
) : ViewModel() {
    private val baseUrl: String = Constants.LINK.POKEMOMIMAGE
    var mListPokemon = MutableLiveData<ArrayList<PokemonResultModel>>()
    var mListPokemonDataBase = MutableLiveData<ArrayList<PokemonResultModel>>()
    var mListPokemonFilter: ArrayList<PokemonResultModel> = arrayListOf()
    var mListAux: List<PokemonResultModel> = arrayListOf()
    var mKeepLoad = MutableLiveData<Boolean>()
    var mSearchViewNull = MutableLiveData<Boolean>()
    var mPokemonRepository = PokemonRepository()
    var mWhereData = MutableLiveData<Int>()
    var mErro: String = ""

    //carregar a lista de pokemons ( se haver local pega localmente) (se não pega remotamente)
    fun loadPokemons() {
        GlobalScope.launch(Dispatchers.IO) {
            mWhereData.postValue(dataBaseIsValid(System.currentTimeMillis())!!)
            when (mWhereData.value) {
                2 -> {
                    mListPokemonDataBase.postValue(ArrayList(dataBase.pokemonDao().getAll()))
                    delay(4000)
                    mKeepLoad.postValue(false)
                }
                else -> {
                    dataBase.pokemonDao().deleteAllPokemons()
                    var s = "x"
                    getPokemonApi()
                }
            }
        }
    }

    //coletando os dados da api / inserindo eles no Room
    fun getPokemonApi() {
        GlobalScope.launch(Dispatchers.IO) {
            getPokemons(Constants.VALUES.SIZE).catch {

            }.collect {
                //inserindo os pokemons dentro do banco de dados
                mListPokemonDataBase.postValue(it)
                dataBase.pokemonDao().addListPokemon(it)
                dataStore.storeTime(System.currentTimeMillis())
            }
        }
    }

    fun tradeMemory() {
        mListPokemon.value = mListPokemonDataBase.value
        var s = ""

    }

    //obtendo pokemons da api
    fun getPokemons(size: Int): Flow<ArrayList<PokemonResultModel>> {
        var defaultPokemon = PokemonResultModel()
        var sendPokemon = ArrayList<PokemonResultModel>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(4000)
            mKeepLoad.postValue(false)
        }
        return flow<ArrayList<PokemonResultModel>> {
            for (x in 1..size) {
                mPokemonRepository.pokemon(
                    x.toString(),
                    object : APIListener<PokemonResultModel> {
                        override suspend fun onSuccess(mode: PokemonResultModel) {
                            mode.image = "$baseUrl${mode.id.toString()}.png"
                            sendPokemon.add(mode)
                            if (sendPokemon.size == size) {
                                emit(sendPokemon)
                            }

                        }

                        override suspend fun onFailure(str: String) {
                            mErro = "ApiErro"
                            defaultPokemon.id = x
                            sendPokemon.add(defaultPokemon)
                            if (sendPokemon.size == size) {
                                emit(sendPokemon)
                            }
                        }
                    })
            }
        }
    }

    fun filter(query: String?) {
        if (query != null && query.isNotEmpty()) {
            val filtered = mListPokemon.value?.filter {
                it.name?.contains(query) ?: false
            }
            mListPokemonFilter.clear()
            mListPokemonFilter
            mListPokemonFilter.addAll(filtered as ArrayList<PokemonResultModel>)
            mListPokemon.value = mListPokemonFilter
        } else {
            var s = ""
            GlobalScope.launch(Dispatchers.IO) {
                mListPokemon.postValue(ArrayList(dataBase.pokemonDao().getAll()))
            }
        }
    }

    //fazer validação se o banco de dados do aplicativo esta muito obsoleto
    suspend fun dataBaseIsValid(currentTime: Long): Int {
        return validation.cacheIsValid(currentTime)
    }

}