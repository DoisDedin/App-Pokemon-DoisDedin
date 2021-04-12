package com.example.pokemon_doisdedin.services.room.dao

import androidx.room.*
import androidx.room.Dao
import com.example.pokemon_doisdedin.services.room.PokemonEntity

@Dao
interface PokemonDAO {

    @Query("SELECT * FROM pokemon") fun getAllPokemon(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertPokemon(vararg pokemonresults: PokemonEntity)

    @Update
    fun updateUser(pokemonresult: PokemonEntity)

    @Delete
    fun deleteUser(pokemonresult: PokemonEntity)
}
@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonsDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDAO
}