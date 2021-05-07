package com.example.pokemon_doisdedin.viewmodel


import androidx.lifecycle.*
import com.example.pokemon_doisdedin.services.auxiliares.ValidationTime
import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.services.listener.APIListener
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.services.repository.PokemonRepository
import com.example.pokemon_doisdedin.services.repository.local.datastore.DataStoreRepositoryLocal
import com.example.pokemon_doisdedin.services.repository.local.room.dao.PokemonsDataBase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


class MainActivityViewModel(
    var dataBase: PokemonsDataBase,
    var dataStore: DataStoreRepositoryLocal,
    var validation: ValidationTime
) : ViewModel() {
    private val baseUrl: String = Constants.LINK.POKEMOMIMAGE
    var mListPokemon = MutableLiveData<ArrayList<PokemonResultModel>>()
    var mListPokemonFilter: ArrayList<PokemonResultModel> = arrayListOf()
    var mKeepLoad = MutableLiveData<Boolean>()
    var mPokemonRepository = PokemonRepository()
    var mWhereData = MutableLiveData<Int>()

    //carregar a lista de pokemons ( se haver local pega localmente) (se não pega remotamente)
    fun loadPokemons() {
        mKeepLoad.value = true
        val currentTime = System.currentTimeMillis()
        GlobalScope.launch(Dispatchers.IO) {
            mWhereData.postValue(validation.cacheIsValid(currentTime))
            when (mWhereData.value) {
                2 -> {
                    mListPokemon.postValue(ArrayList(dataBase.pokemonDao().getAll()))
                    mKeepLoad.postValue(false)
                }
                else -> {
                    dataBase.pokemonDao().deleteAllPokemons()
                    getPokemonApi()
                }
            }
        }
    }

    fun loadSuccess() {
        mKeepLoad.value = false
    }

    //coletando os dados da api / inserindo eles no Room
   private fun getPokemonApi() {
        GlobalScope.launch(Dispatchers.IO) {
            getPokemons(Constants.VALUES.SIZE).catch {

            }.collect {
                //inserindo os pokemons dentro do banco de dados
                mListPokemon.postValue(it)
                dataBase.pokemonDao().addListPokemon(it)
                dataStore.storeTime(System.currentTimeMillis())
            }
        }
    }

    //obtendo pokemons da api
    private fun getPokemons(sizePokemon: Int): Flow<ArrayList<PokemonResultModel>> {
        val defaultPokemon = PokemonResultModel()
        val sendPokemon = ArrayList<PokemonResultModel>()

        return flow {
            for (x in 1..sizePokemon) {
                mPokemonRepository.pokemon(
                    x.toString(),
                    object : APIListener<PokemonResultModel> {
                        override suspend fun onSuccess(mode: PokemonResultModel) {
                            mode.image = "$baseUrl${mode.id.toString()}.png"
                            sendPokemon.add(mode)
                            if (sendPokemon.size == sizePokemon) {
                                emit(sendPokemon)
                            }

                        }

                        override suspend fun onFailure(str: String) {
                            defaultPokemon.id = x
                            sendPokemon.add(defaultPokemon)
                            if (sendPokemon.size == sizePokemon) {
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
            GlobalScope.launch(Dispatchers.IO) {
                mListPokemon.postValue(ArrayList(dataBase.pokemonDao().getAll()))
            }
        }
    }

}