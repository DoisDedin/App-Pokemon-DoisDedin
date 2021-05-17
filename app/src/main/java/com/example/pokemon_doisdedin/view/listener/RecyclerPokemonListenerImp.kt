package com.example.pokemon_doisdedin.view.listener

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.view.PokemonActivity

class RecyclerPokemonListenerImp : RecyclerPokemonListener {
    override fun onClick(id: Int, view: View) {
        val bundle = Bundle()
        bundle.putInt(
            Constants.BUNDLE.ID,
            id
        )
        val intent = Intent(view.context, PokemonActivity::class.java)
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }
}