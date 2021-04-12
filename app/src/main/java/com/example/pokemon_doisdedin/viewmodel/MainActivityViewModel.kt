package com.example.pokemon_doisdedin.viewmodel

import android.app.Application
import android.content.Context
import android.provider.SyncStateContract
import android.widget.Toast
import androidx.lifecycle.*
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.model.PokemonModel
import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.services.listener.APIListener
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.services.repository.PokemonRepository
import com.example.pokemon_doisdedin.services.room.dao.PokemonsDataBase
import kotlinx.coroutines.Delay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivityViewModel(
    var dataBase: PokemonsDataBase
) : ViewModel() {
    private val baseUrl: String = "https://pokeres.bastionbot.org/images/pokemon/"
    var mListPokemon = MutableLiveData<ArrayList<PokemonResultModel>>()
    var mListPokemonFilter: ArrayList<PokemonResultModel> = arrayListOf()
    var mKeepLoad = MutableLiveData<Boolean>()
    var mSearchViewNull = MutableLiveData<Boolean>()
    var mPokemonRepository = PokemonRepository()
    var mErro: String = ""


    fun pokemon() {
        GlobalScope.launch {
            getPokemons(Constants.VALUES.SIZE).catch {

            }.collect {
                mListPokemon.postValue(it)
            }
        }
    }

    //obtendo pokemons da api
    fun getPokemons(size: Int): Flow<ArrayList<PokemonResultModel>> {
        var defaultPokemon = PokemonResultModel()
        var sendPokemon = ArrayList<PokemonResultModel>()
        GlobalScope.launch {
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
            pokemon()
        }
    }

}