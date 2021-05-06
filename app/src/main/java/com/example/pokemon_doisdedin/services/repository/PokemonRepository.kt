package com.example.pokemon_doisdedin.services.repository

import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.services.listener.APIListener
import com.example.pokemon_doisdedin.services.repository.local.room.entity.PokemonResultModel
import com.example.pokemon_doisdedin.services.repository.remoto.PokemonService
import com.example.pokemon_doisdedin.services.repository.remoto.RetrofitPokemon
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository {
    private val mRemote = RetrofitPokemon.createService(PokemonService::class.java)
    suspend fun pokemon(id: String, listener: APIListener<PokemonResultModel>) {
        val response: Response<PokemonResultModel> = mRemote.pokemon(id)
//        call.(object : Callback<PokemonResultModel> {
//            override suspend fun onResponse(
//                call: Call<PokemonResultModel>,
//                response: Response<PokemonResultModel>
//            ) {
//                if (response.code() != Constants.HTTP.SUCCESS) {
//                    val validation =
//                        Gson().fromJson(response.errorBody().toString(), String::class.java)
//                    listener.onFailure(validation)
//                } else {
//                    response.body()?.let { listener.onSuccess(it) }
//                }
//            }
//
//            override fun onFailure(call: Call<PokemonResultModel>, t: Throwable) {
//                listener.onFailure(t.message.toString())
//            }
//
//        })
        if (response.isSuccessful) {
            response.body()?.let { listener.onSuccess(it) }
        } else {
            listener.onFailure(response.message())
        }
    }
}