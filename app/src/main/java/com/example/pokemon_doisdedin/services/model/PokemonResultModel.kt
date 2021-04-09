package com.example.pokemon_doisdedin.services.model

import com.google.gson.annotations.SerializedName

class PokemonResultModel {
    @SerializedName("id")
    var id: Int? = 0
    @SerializedName("name")
    var name: String? = "Defautl"

    @SerializedName("height")
    var height: Int? = 0
    @SerializedName("weight")
    var weight: Int? = 0
    @SerializedName("base_experience")
    var base_experience: Int? = 0
    var image: String? = "Defautl"
}