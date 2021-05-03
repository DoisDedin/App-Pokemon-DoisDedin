package com.example.pokemon_doisdedin.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.view.listener.RecyclerPokemonListener
import com.example.pokemon_doisdedin.view.viewadapter.RecyclerPokemonAdapter
import com.example.pokemon_doisdedin.viewmodel.MainActivityViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
        GlobalScope.launch {
            mViewModel.loadPokemons()
        }
        observeViewModel()
        observeSearchView()
    }

    override fun onResume() {
        super.onResume()
        setLayout(true)
       // mViewModel.loadPokemons()
        observeViewModel()
        observeSearchView()
    }

    override fun onPause() {
        super.onPause()

        var x = Calendar.getInstance().time

    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun observeViewModel() {
        mViewModel.mListPokemon.observe(this, Observer {
            recyler_pokemons?.adapter = mRecyclerPokemonAdapter
            mRecyclerPokemonAdapter.setList(it)

        })
        mViewModel.mKeepLoad.observe(this, Observer {
            if (it == false){
                setLayout(false)
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
        }else{
            recyler_pokemons?.visibility = View.GONE
            search_pokemon?.visibility = View.GONE
            lottieRecycler?.visibility = View.VISIBLE
        }
    }
}



