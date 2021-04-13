package com.example.pokemon_doisdedin.services.koin

import androidx.room.Room
import com.example.pokemon_doisdedin.services.room.dao.PokemonsDataBase
import com.example.pokemon_doisdedin.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    viewModel {
        MainActivityViewModel(
            dataBase = get()
        )
    }
    single { get<PokemonsDataBase>().pokemonDao() }
    single {
        Room.databaseBuilder(get(), PokemonsDataBase::class.java, "pokemons_db")
            .fallbackToDestructiveMigration().build()
    }
}