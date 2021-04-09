package com.example.pokemon_doisdedin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mListenerRecyclerPokemon: RecyclerPokemonListener
    private lateinit var mRecyclerPokemonAdapter: RecyclerPokemonAdapter
    var lottieRecycler: LottieAnimationView? = null
    var recyler_pokemons: RecyclerView? = null
    var grid_layout_manager: GridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mRecyclerPokemonAdapter = RecyclerPokemonAdapter()
        mListenerRecyclerPokemon = object : RecyclerPokemonListener {
            override fun onClick(id: Int) {
                //nothing
            }
        }
        //preparando o terreno
        recyler_pokemons = findViewById(R.id.recycler_pokemons)
        lottieRecycler = findViewById(R.id.lottieRecycler)
        grid_layout_manager = GridLayoutManager(this, 2)
        recyler_pokemons?.layoutManager = grid_layout_manager

        mRecyclerPokemonAdapter.setListener(mListenerRecyclerPokemon)

        GlobalScope.launch {
            mViewModel.getPokemons(30)
        }
        setLayout()
        observeViewModel()
    }

    fun observeViewModel() {
        mViewModel.mListPokemon.observe(this, Observer {
            recyler_pokemons?.adapter = mRecyclerPokemonAdapter
            mRecyclerPokemonAdapter.setList(it)

        })

    }

    private fun setLayout() {

        mViewModel.mKeepLoad.observe(this, Observer {
            if (it == false) {
                recyler_pokemons?.visibility = View.VISIBLE
                lottieRecycler?.visibility = View.GONE
            }
        })

    }


}
