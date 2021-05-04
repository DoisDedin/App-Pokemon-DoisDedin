package com.example.pokemon_doisdedin.view

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.view.listener.RecyclerPokemonListener
import com.example.pokemon_doisdedin.view.viewadapter.RecyclerPokemonAdapter
import com.example.pokemon_doisdedin.viewmodel.MainActivityViewModel
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.util.*


class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModel<MainActivityViewModel>()
    private val mRecyclerPokemonAdapter: RecyclerPokemonAdapter by inject()
    var lottieRecycler: LottieAnimationView? = null
    var recyler_pokemons: RecyclerView? = null
    var grid_layout_manager: GridLayoutManager? = null
    var search_pokemon: SearchView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //preparando o terreno
        recyler_pokemons = findViewById(R.id.recycler_pokemons)
        lottieRecycler = findViewById(R.id.lottieRecycler)
        search_pokemon = findViewById(R.id.searchview_pokemons)
        grid_layout_manager = GridLayoutManager(this, 2)
        recyler_pokemons?.layoutManager = grid_layout_manager


        observeViewModel()
        observeSearchView()



    }

    override fun onResume() {
        super.onResume()

        mViewModel.loadPokemons()
        Toast.makeText(this, "resume", Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        setLayout(false)
        Toast.makeText(this, "pause", Toast.LENGTH_LONG).show()


    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "destroy", Toast.LENGTH_LONG).show()
    }


    private fun observeViewModel() {
        mViewModel.mListPokemon.observe(this, Observer {
            recyler_pokemons?.adapter = mRecyclerPokemonAdapter
            mRecyclerPokemonAdapter.setList(it)

        })
        mViewModel.mKeepLoad.observe(this, Observer {
            if (it == false) {
                setLayout(false)
            }else {
                setLayout(true)
            }
        })
        mViewModel.mLook.observe(this, Observer {
            if (it == "local_data") {
                Toast.makeText(this, "local data", Toast.LENGTH_LONG).show()
            } else if (it == "remote_data") {
                Toast.makeText(this, "remote data", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun observeSearchView() {
        search_pokemon?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mViewModel.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mViewModel.filter(newText)
                return false
            }
        })
    }

    private fun setLayout(boolean: Boolean) {
        if (boolean == false) {
            recyler_pokemons?.visibility = View.VISIBLE
            search_pokemon?.visibility = View.VISIBLE
            lottieRecycler?.visibility = View.GONE
        } else {
            recyler_pokemons?.visibility = View.GONE
            search_pokemon?.visibility = View.GONE
            lottieRecycler?.visibility = View.VISIBLE
        }
    }
}



