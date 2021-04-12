package com.example.pokemon_doisdedin.view.viewholder

import android.icu.number.NumberFormatter.with
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.view.listener.RecyclerPokemonListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.coroutineContext

class RecyclerPokemonHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name_pokemon: TextView = view.findViewById(R.id.name_pokemon)
    private val image_pokemon: ImageView = view.findViewById(R.id.image_pokemon)
    private val lottie: LottieAnimationView = view.findViewById(R.id.lottie)
    var mKeepLoad = MutableLiveData<Boolean>()
    fun bind(pokemon: PokemonResultModel, listener: RecyclerPokemonListener) {
        setLayout(0)
        name_pokemon.text = pokemon.name

        var urlImage =
            //fazer a atribuição da imagem corretamente image_pokemon.setImageResource(R.drawable.androidzinho_grey)
            Picasso.get().load(pokemon.image)
                .into(image_pokemon, object : Callback {
                    override fun onSuccess() {
                        setLayout(1)
                    }
                    override fun onError(e: Exception?) {
                    }
                })
        name_pokemon.setOnClickListener {
            listener.onClick(1)
        }
        //changing the layout if the pokemon is loaded
    }

    private fun setLayout(int: Int) {
        if (int == 1) {
            name_pokemon.visibility = View.VISIBLE
            image_pokemon.visibility = View.VISIBLE
            lottie.visibility = View.GONE
        } else {
            name_pokemon.visibility = View.GONE
            image_pokemon.visibility = View.GONE
            lottie.visibility = View.VISIBLE
        }

    }
}