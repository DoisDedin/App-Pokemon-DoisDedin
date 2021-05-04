package com.example.pokemon_doisdedin.viewmodel


import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.pokemon_doisdedin.services.auxiliares.ValidacaoTempo

import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.services.listener.APIListener
import com.example.pokemon_doisdedin.services.repository.local.room.entity.PokemonResultModel
import com.example.pokemon_doisdedin.services.repository.PokemonRepository
import com.example.pokemon_doisdedin.services.repository.local.datastore.DataStoreRepository
import com.example.pokemon_doisdedin.services.repository.local.room.dao.PokemonsDataBase
import kotlinx.coroutines.*

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okhttp3.Dispatcher


class MainActivityViewModel(
    var application: Application,
    var dataBase: PokemonsDataBase,
    var dataStore: DataStoreRepository,
    var validation: ValidacaoTempo
) : ViewModel() {
    private val baseUrl: String = Constants.LINK.POKEMOMIMAGE
    var mLook = MutableLiveData<String>()
    var mListPokemon = MutableLiveData<ArrayList<PokemonResultModel>>()
    var mListPokemonFilter: ArrayList<PokemonResultModel> = arrayListOf()
    var mListAux: List<PokemonResultModel> = arrayListOf()
    var mKeepLoad = MutableLiveData<Boolean>()
    var mSearchViewNull = MutableLiveData<Boolean>()
    var mPokemonRepository = PokemonRepository()
    var mErro: String = ""

    //banco de dados auxiliares
    //carregar a lista de pokemons ( se haver local pega localmente) (se não pega remotamente)
    fun loadPokemons() {

        mKeepLoad.value = true
        var currentTime = System.currentTimeMillis()
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                if (dataBaseIsValid(currentTime)) {
                    mListPokemon.postValue(ArrayList(dataBase.pokemonDao().getAll()))

                    mKeepLoad.postValue(false)
                    mLook.postValue("local_data")

                } else {
                    dataStore.storeTime(currentTime)
                    getPokemon()
                    mLook.postValue("remote_data")

                }
            }
        }
    }

    //coletando os dados da api / inserindo eles no Room
    suspend fun getPokemon() {
        getPokemonsApi(Constants.VALUES.SIZE).catch {

        }.collect {
            //inserindo os pokemons dentro do banco de dados
            mListPokemon.postValue(it)
            dataBase.pokemonDao().addListPokemon(it)
        }

    }

    //obtendo pokemons da api
    fun getPokemonsApi(size: Int): Flow<ArrayList<PokemonResultModel>> {
        var defaultPokemon = PokemonResultModel()
        var sendPokemon = ArrayList<PokemonResultModel>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(4000)
            mKeepLoad.postValue(false)
        }
        return flow<ArrayList<PokemonResultModel>> {
            for (x in 1..size) {
                mPokemonRepository.pokemon(x.toString(), object : APIListener<PokemonResultModel> {
                    override fun onSuccess(mode: PokemonResultModel) {
                        mode.image = "$baseUrl${mode.id.toString()}.png"
                        sendPokemon.add(mode)
                    }

                    override fun onFailure(str: String) {
                        mErro = "ApiErro"
                        defaultPokemon.id = x
                        sendPokemon.add(defaultPokemon)
                    }
                })
            }
            emit(sendPokemon)
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
            GlobalScope.launch(Dispatchers.IO) {
                mListPokemon.postValue(ArrayList(dataBase.pokemonDao().getAll()))
            }
        }
    }

    //fazer validação se o banco de dados do aplicativo esta  obsoleto
    suspend fun dataBaseIsValid(currentTime: Long): Boolean {
        return false//validation.cacheIsValid(currentTime)
    }

}