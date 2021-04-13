package com.example.pokemon_doisdedin.view.viewholder


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.view.listener.RecyclerPokemonListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class RecyclerPokemonHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name_pokemon: TextView = view.findViewById(R.id.name_pokemon)
    private val image_pokemon: ImageView = view.findViewById(R.id.image_pokemon)
    private val lottie: LottieAnimationView = view.findViewById(R.id.lottie)
    private val background: ConstraintLayout = view.findViewById(R.id.background_gambiarra)
    fun bind(pokemon: PokemonResultModel, listener: RecyclerPokemonListener) {
        setLayout(0)
        name_pokemon.text = pokemon.name
        //fazer a atribuição da imagem corretamente image_pokemon.setImageResource(R.drawable.androidzinho_grey)
        Picasso.get().load(pokemon.image)
            .into(image_pokemon, object : Callback {
                override fun onSuccess() {
                    loadPalette()
                }

                override fun onError(e: Exception?) {
                }
            })
        name_pokemon.setOnClickListener {
            listener.onClick(1)
        }
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

    private fun loadPalette() {
        var drawable: BitmapDrawable = image_pokemon.drawable as BitmapDrawable
        var bitMap: Bitmap = drawable.bitmap

        var builder: Palette.Builder = Palette.Builder(bitMap)
        builder.generate(object : Palette.PaletteAsyncListener {
            override fun onGenerated(palette: Palette?) {
                var lightMuted: Palette.Swatch? = palette?.lightMutedSwatch
                var muted: Palette.Swatch? = palette?.mutedSwatch
                if (lightMuted != null) {
                    background.background.setTint(lightMuted.rgb)
                    name_pokemon.setTextColor(lightMuted.titleTextColor)
                } else if (muted != null) {
                    background.background.setTint(muted.rgb)
                    name_pokemon.setTextColor(muted.titleTextColor)
                }
                setLayout(1)
            }
        })

    }
}