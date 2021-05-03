package com.example.pokemon_doisdedin.services.koin

import androidx.room.Room
import com.example.pokemon_doisdedin.services.auxiliares.ValidacaoTempo
import com.example.pokemon_doisdedin.services.repository.local.datastore.DataStoreRepository
import com.example.pokemon_doisdedin.services.repository.local.room.dao.PokemonsDataBase
import com.example.pokemon_doisdedin.view.listener.RecyclerPokemonListener
import com.example.pokemon_doisdedin.view.listener.RecyclerPokemonListenerImp
import com.example.pokemon_doisdedin.view.viewadapter.RecyclerPokemonAdapter
import com.example.pokemon_doisdedin.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object myModule {
    val modules = module {
        viewModel {
            MainActivityViewModel(
                application = get(),
                dataBase = get(),
                dataStore = get(),
                validation = get()
            )
        }

        factory { ValidacaoTempo(get()) }
        single { DataStoreRepository(get()) }
        single { get<PokemonsDataBase>().pokemonDao() }
        single {
            Room.databaseBuilder(get(), PokemonsDataBase::class.java, "pokemons_db")
                .fallbackToDestructiveMigration().build()
        }
    }

    val modules1 = module {

        factory { RecyclerPokemonAdapter(RecyclerPokemonListenerImp()) }
    }

    val appComponent = listOf(modules, modules1)
}

