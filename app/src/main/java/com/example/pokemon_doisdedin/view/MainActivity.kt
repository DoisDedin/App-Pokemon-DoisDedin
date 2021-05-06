package com.example.pokemon_doisdedin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.view.listener.RecyclerPokemonListener
import com.example.pokemon_doisdedin.view.viewadapter.RecyclerPokemonAdapter
import com.example.pokemon_doisdedin.viewmodel.MainActivityViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModel()
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

        mViewModel.loadPokemons()

    }

    private fun observeViewModel() {
        mViewModel.mListPokemon.observe(this, Observer {
            var x = it.size
            recyler_pokemons?.adapter = mRecyclerPokemonAdapter
            mRecyclerPokemonAdapter.setList(it)

        })
        mViewModel.mListPokemonDataBase.observe(this, Observer {
            if (it.size != 0){
                mViewModel.tradeMemory()
            }
            Toast.makeText(applicationContext, "memory 0", Toast.LENGTH_SHORT).show()
        })
        mViewModel.mKeepLoad.observe(this, Observer {
            setLayout(it)

        })
        mViewModel.mWhereData.observe(this, Observer {
            when (it) {
                0 -> {
                    Toast.makeText(applicationContext, "null current time", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    Toast.makeText(applicationContext, "invalid local data", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(applicationContext, "full remote data", Toast.LENGTH_SHORT).show()
                }
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
        }
    }
}



