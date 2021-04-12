package com.example.pokemon_doisdedin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.view.listener.RecyclerPokemonListener
import com.example.pokemon_doisdedin.view.viewadapter.RecyclerPokemonAdapter
import com.example.pokemon_doisdedin.viewmodel.MainActivityViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModel()
    private lateinit var mListenerRecyclerPokemon: RecyclerPokemonListener
    private lateinit var mRecyclerPokemonAdapter: RecyclerPokemonAdapter
    var lottieRecycler: LottieAnimationView? = null
    var recyler_pokemons: RecyclerView? = null
    var grid_layout_manager: GridLayoutManager? = null
    var search_pokemon: SearchView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerPokemonAdapter = RecyclerPokemonAdapter()
        mListenerRecyclerPokemon = object : RecyclerPokemonListener {
            override fun onClick(id: Int) {
                //nothing
            }
        }
        //preparando o terreno
        recyler_pokemons = findViewById(R.id.recycler_pokemons)
        lottieRecycler = findViewById(R.id.lottieRecycler)
        search_pokemon = findViewById(R.id.searchview_pokemons)
        grid_layout_manager = GridLayoutManager(this, 2)
        recyler_pokemons?.layoutManager = grid_layout_manager
        mRecyclerPokemonAdapter.setListener(mListenerRecyclerPokemon)

        mViewModel.pokemon()

        setLayout()
        observeViewModel()
        observeSearchView()
    }

    private fun observeViewModel() {
        mViewModel.mListPokemon.observe(this, Observer {
            recyler_pokemons?.adapter = mRecyclerPokemonAdapter
            mRecyclerPokemonAdapter.setList(it)

        })
        mViewModel.mSearchViewNull.observe(this, Observer {
        })
    }

    private fun observeSearchView(){
        search_pokemon?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
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

    private fun setLayout() {

        mViewModel.mKeepLoad.observe(this, Observer {
            if (it == false) {
                recyler_pokemons?.visibility = View.VISIBLE
                search_pokemon?.visibility = View.VISIBLE
                lottieRecycler?.visibility = View.GONE
            }
        })

    }


}
