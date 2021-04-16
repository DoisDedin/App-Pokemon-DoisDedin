package com.example.pokemon_doisdedin.services.repository.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
class PokemonEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "height") var height: Int,
    @ColumnInfo(name = "weight") var weight: Int,
    @ColumnInfo(name = "base_experience") var base_experience: Int,
    @ColumnInfo(name = "image") var image: String
)
