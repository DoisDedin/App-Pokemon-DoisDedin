package com.example.pokemon_doisdedin.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.services.repository.local.room.dao.PokemonsDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PokemonViewModel(
    var application: Application,
    var dataBase: PokemonsDataBase
) : ViewModel() {

    var mPokemon = MutableLiveData<PokemonResultModel>()

    fun getPokemon(id: String) {
        GlobalScope.launch(Dispatchers.IO) {
            mPokemon.postValue(dataBase.pokemonDao().getpokemon(id.toString()))
        }
    }

}