package com.example.pokemon_doisdedin.services.repository.remoto

import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class PokemonServiceImp(
    private val pokemonApi: PokemonApi,
) : PokemonService {
    //coletando os dados da api / inserindo eles no Room
    override fun getPokemonsApi(): Flow<ArrayList<PokemonResultModel>> =
        flow {
            //preparando o resultado das requisições para mandar para a camada de repositorio
            val defaultPokemon = PokemonResultModel()
            val myLIstPokemon = ArrayList<PokemonResultModel>()
            val size = Constants.VALUES.SIZE
            getPokemonsSend(size).catch {
                myLIstPokemon.add(defaultPokemon)
                if (myLIstPokemon.size == size) {
                    emit(myLIstPokemon)
                }
            }.collect { mode ->
                mode.image = "${size}${mode.id.toString()}.png"
                myLIstPokemon.add(mode)
                if (myLIstPokemon.size == size) {
                    emit(myLIstPokemon)
                }
            }
        }

    private fun getPokemonsSend(size: Int): Flow<PokemonResultModel> =
        flow {
            for (x in 1..size) {
                emit(pokemonApi.pokemon(x.toString()))
            }
        }
}
