package com.example.pokemon_doisdedin.services.repository.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemon")
class PokemonResultModel {
    @SerializedName("id")
    @PrimaryKey
    var id: Int? = 0

    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String? = "Defautl"

    @ColumnInfo(name = "height")
    @SerializedName("height")
    var height: Int? = 0

    @ColumnInfo(name = "weight")
    @SerializedName("weight")
    var weight: Int? = 0

    @ColumnInfo(name = "base_experience")
    @SerializedName("base_experience")
    var base_experience: Int? = 0

    @ColumnInfo(name = "image")
    var image: String? = "Defautl"
}