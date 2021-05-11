package com.example.pokemon_doisdedin.services.repository.local.room.dao

import androidx.room.*
import androidx.room.Dao

import com.example.pokemon_doisdedin.services.model.PokemonResultModel

@Dao
interface PokemonDAO {

    @Insert
    suspend fun addListPokemon(entities:List<PokemonResultModel>)
    @Query("SELECT * FROM pokemon")
    fun getAll(): List<PokemonResultModel>
    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemons()
    @Query("SELECT * FROM pokemon WHERE id in (:id)")
    fun getpokemon(id: String): PokemonResultModel
}
@Database(entities = [PokemonResultModel::class], version = 3)
abstract class PokemonsDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDAO
}