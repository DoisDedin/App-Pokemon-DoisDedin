package com.example.pokemon_doisdedin.services.koin

import androidx.room.Room
import com.example.pokemon_doisdedin.services.auxiliares.ValidationTime
import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.services.repository.local.datastore.DataStoreRepositoryLocal
import com.example.pokemon_doisdedin.services.repository.local.datastore.UserSettingsStore
import com.example.pokemon_doisdedin.services.repository.local.datastore.UserSettingsStoreImp
import com.example.pokemon_doisdedin.services.repository.local.room.dao.PokemonsDataBase
import com.example.pokemon_doisdedin.services.repository.remoto.PokemonApi
import com.example.pokemon_doisdedin.services.repository.remoto.PokemonServiceImp
import com.example.pokemon_doisdedin.services.repository.remoto.RetrofitPokemon
import com.example.pokemon_doisdedin.view.listener.RecyclerPokemonListenerImp
import com.example.pokemon_doisdedin.view.viewadapter.RecyclerPokemonAdapter
import com.example.pokemon_doisdedin.viewmodel.MainActivityViewModel
import com.example.pokemon_doisdedin.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object myModule {
    private val modules = module {
        viewModel {
            MainActivityViewModel(
                dataBase = get(),
                dataStore = get(),
                validation = get()
            )

        }
        viewModel {
            PokemonViewModel(
                application = get(),
                dataBase = get()
            )
        }
        factory { ValidationTime(get()) }
        single { UserSettingsStoreImp(get()) }
        single { get<PokemonsDataBase>().pokemonDao() }
        single {
            Room.databaseBuilder(get(), PokemonsDataBase::class.java, Constants.DATA_BASE.NAME_DATA_BASE)
                .fallbackToDestructiveMigration().build()
        }
        single { PokemonServiceImp(RetrofitPokemon.createService(PokemonApi) }

    }
   private val modules1 = module {
        factory { RecyclerPokemonAdapter(RecyclerPokemonListenerImp()) }
    }
    val appComponent = listOf(modules, modules1)
}
