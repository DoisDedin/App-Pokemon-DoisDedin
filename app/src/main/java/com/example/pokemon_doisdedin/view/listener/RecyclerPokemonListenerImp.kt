package com.example.pokemon_doisdedin.view.listener

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.view.PokemonActivity

class RecyclerPokemonListenerImp: RecyclerPokemonListener {
    override fun onClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt(
            Constants.BUNDLE.ID,
            id
        )
        val intent = Intent(PRECISO DE UM CONTEXT, PokemonActivity::class.java)
        intent.putExtras(bundle)
        PRECISO DE UM CONTEXT.startActivity(intent)
    }
}