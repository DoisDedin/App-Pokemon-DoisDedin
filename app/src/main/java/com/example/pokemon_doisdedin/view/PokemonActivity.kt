package com.example.pokemon_doisdedin.view


import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import com.airbnb.lottie.LottieAnimationView
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.databinding.ActivityPokemonBinding
import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.viewmodel.PokemonViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class PokemonActivity : AppCompatActivity(), View.OnClickListener {

    private val mViewModel: PokemonViewModel by viewModel()
    lateinit var binding: ActivityPokemonBinding

    //    private var fab: FloatingActionButton? = null
//    private var imagePokemon: ImageView? = null
//    private var txtNamePokemon: TextView? = null
//    private var txtPhrase: TextView? = null
//    private var txtDemagePokemon: TextView? = null
//    private var progressDemagePokemon: ProgressBar? = null
//    private var txtDefensePokemon: TextView? = null
//    private var progressDefensePokemon: ProgressBar? = null
//    private var txtLifePokemon: TextView? = null
//    private var progressLifePokemon: ProgressBar? = null
//    private var txtHeightPokemon: TextView? = null
//    private var progressHeightPokemon: ProgressBar? = null
//    private var txtWeightPokemon: TextView? = null
//    private var progressWeightPokemon: ProgressBar? = null
//    private var txtBaseXpPokemon: TextView? = null
//    private var progressBaseXpPokemon: ProgressBar? = null
//
//    private var background: ConstraintLayout? = null
//    private var lottie: LottieAnimationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //bindingLayout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon)

        //getBundleValues
        val bundle = intent.extras
        val idPokemon = bundle?.get(Constants.BUNDLE.ID)

        setListeners()
        observeViewModel()

        getPokemonViewModel(idPokemon.toString())

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.floating_action_button) {
            finish()
        }
    }

    private fun setListeners() {
        binding.floatingActionButton?.setOnClickListener(this)
    }

    private fun observeViewModel() {
        mViewModel.mPokemon.observe(this, {
            setValuesLayout(it)
        })
    }

    private fun getPokemonViewModel(id: String) {
        mViewModel.getPokemon(id)
    }

    fun setValuesLayout(pokemon: PokemonResultModel) {
        binding.apply {


            layoutPokemon.namePokemon.text = pokemon.name
            frasepokemon.text = " esse é o ${pokemon.name}"
            setLayout(0)
            Picasso.get().load(pokemon.image)
                .into(layoutPokemon.imagePokemon, object : Callback {
                    override fun onSuccess() {
                        loadPalette()
                        var s = ""
                    }

                    override fun onError(e: Exception?) {
                    }
                })
            val demage = rand(0, 1000)
            demageInt.text = "$demage ATK"
            progressAnimator(demageBar, demage)

            val defense = rand(0, 1000)
            defenseInt.text = "$defense DEF"
            progressAnimator(defenseBar, defense)

            val life = rand(0, 1000)
            lifeInt.text = "$life HP"
            progressAnimator(lifeBar, life)

            val height = pokemon.height.toString() + "0"
            heightInt.text = "$height Cm"
            progressAnimator(heightBar, height.toInt())

            val weight = pokemon.weight.toString()
            weightInt.text = "$weight Kg"
            progressAnimator(weightBar, weight.toInt())

            val basexp = pokemon.base_experience.toString()
            baseXpInt.text = "$basexp Xp"
            progressAnimator(baseXpBar, basexp.toInt())


        }
    }

    private fun rand(from: Int, to: Int): Int {
        val random = Random()
        return random.nextInt(to - from) + from // from(incluso) e to(excluso)
    }

    private fun loadPalette() {
        val drawable: BitmapDrawable = binding.layoutPokemon.imagePokemon.drawable as BitmapDrawable
        val bitMap: Bitmap = drawable.bitmap

        val builder: Palette.Builder = Palette.Builder(bitMap)
        builder.generate { palette ->
            val lightMuted: Palette.Swatch? = palette?.lightMutedSwatch
            val muted: Palette.Swatch? = palette?.mutedSwatch
            if (lightMuted != null) {
                binding.layoutPokemon.backgroundGambiarra.background.setTint(lightMuted.rgb)
                binding.layoutPokemon.namePokemon.setTextColor(lightMuted.titleTextColor)
            } else if (muted != null) {
                binding.layoutPokemon.backgroundGambiarra.background.setTint(muted.rgb)
                binding.layoutPokemon.namePokemon.setTextColor(muted.titleTextColor)
            }
            setLayout(1)
        }

    }

    private fun setLayout(int: Int) {
        if (int == 1) {
            binding.layoutPokemon.namePokemon.visibility = View.VISIBLE
            binding.layoutPokemon.imagePokemon.visibility = View.VISIBLE
            binding.layoutPokemon.lottie.visibility = View.GONE
        } else {
            binding.layoutPokemon.namePokemon.visibility = View.GONE
            binding.layoutPokemon.imagePokemon.visibility = View.GONE
            binding.layoutPokemon.lottie.visibility = View.VISIBLE
        }
    }

    private fun progressAnimator(pb: ProgressBar?, info: Int) {
        pb?.max = 1000
        ObjectAnimator.ofInt(pb, "progress", info).setDuration(2000).start()
    }


}