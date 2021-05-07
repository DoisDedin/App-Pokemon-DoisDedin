package com.example.pokemon_doisdedin.view.viewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.view.listener.RecyclerPokemonListenerImp
import com.example.pokemon_doisdedin.view.viewholder.RecyclerPokemonHolder

class RecyclerPokemonAdapter(
    private var mListener: RecyclerPokemonListenerImp
) : RecyclerView.Adapter<RecyclerPokemonHolder>() {
    private lateinit var mListPokemon: List<PokemonResultModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecyclerPokemonHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.pokemon, parent, false)
    )

    override fun onBindViewHolder(holder: RecyclerPokemonHolder, position: Int) {
        holder.bind(mListPokemon[position], mListener)
    }

    override fun getItemCount(): Int {
        return mListPokemon.size
    }

    fun setList(list: List<PokemonResultModel>) {
        list.also { mListPokemon = it }
    }
    fun addAdapterToPokemon(idPokemon: Int){
        mListener
    }
}