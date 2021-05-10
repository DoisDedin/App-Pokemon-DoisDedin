package com.example.pokemon_doisdedin.view


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonActivity : AppCompatActivity(), View.OnClickListener {
    private val mViewModel: PokemonViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
    }

    override fun onClick(v: View?) {

    }
}