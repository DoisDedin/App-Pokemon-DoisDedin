package com.example.pokemon_doisdedin.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.model.PokemonModel
import com.example.pokemon_doisdedin.services.listener.APIListener
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.services.repository.PokemonRepository
import kotlinx.coroutines.Delay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    var mListPokemon = MutableLiveData<ArrayList<PokemonResultModel>>()
    var mKeepLoad = MutableLiveData<Boolean>()
    var mListPokemonAux = ArrayList<PokemonResultModel>()
    var mPokemonRepository = PokemonRepository()
    var mErro: String = ""

    //obtendo pokemons da api
    fun getPokemons(size: Int) {
        //do when mutable a variable //responsible when load first in application
        GlobalScope.launch {
            delay(2000)
            mKeepLoad.postValue(false)
        }
        var defaultPokemon = PokemonResultModel()
        for (x in 1..50) {

            mPokemonRepository.pokemon(x.toString(), object : APIListener<PokemonResultModel> {
                override fun onSuccess(mode: PokemonResultModel) {
                    mListPokemonAux.add(mode)
                    mListPokemon.value = mListPokemonAux
                }
                override fun onFailure(str: String) {
                    mErro = "ApiErro"
                    mListPokemonAux.add(defaultPokemon)
                    mListPokemon.value = mListPokemonAux
                }
            })
        }
    }


}