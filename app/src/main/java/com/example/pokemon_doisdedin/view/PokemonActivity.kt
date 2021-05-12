package com.example.pokemon_doisdedin.view


import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getDrawable
import androidx.palette.graphics.Palette
import com.airbnb.lottie.LottieAnimationView
import com.example.pokemon_doisdedin.R
import com.example.pokemon_doisdedin.services.constants.Constants
import com.example.pokemon_doisdedin.services.model.PokemonResultModel
import com.example.pokemon_doisdedin.viewmodel.PokemonViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class PokemonActivity : AppCompatActivity(), View.OnClickListener {

    private val mViewModel: PokemonViewModel by viewModel()

    private var fab: FloatingActionButton? = null
    private var imagePokemon: ImageView? = null
    private var txtNamePokemon: TextView? = null
    private var txtPhrase: TextView? = null
    private var txtDemagePokemon: TextView? = null
    private var progressDemagePokemon: ProgressBar? = null
    private var txtDefensePokemon: TextView? = null
    private var progressDefensePokemon: ProgressBar? = null
    private var txtLifePokemon: TextView? = null
    private var progressLifePokemon: ProgressBar? = null
    private var txtHeightPokemon: TextView? = null
    private var progressHeightPokemon: ProgressBar? = null
    private var txtWeightPokemon: TextView? = null
    private var progressWeightPokemon: ProgressBar? = null
    private var txtBaseXpPokemon: TextView? = null
    private var progressBaseXpPokemon: ProgressBar? = null

    private var background: ConstraintLayout? = null
    private var lottie: LottieAnimationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
        val bundle = intent.extras
        var idPokemon = bundle?.get(Constants.BUNDLE.ID)

        bindLayout()
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
        fab?.setOnClickListener(this)
    }

    private fun observeViewModel() {
        mViewModel.mPokemon.observe(this, {
            setValuesLayout(it)
        })
    }

    private fun getPokemonViewModel(id: String) {
        mViewModel.getPokemon(id)
    }

    fun bindLayout() {
        //basic layouts
        background = findViewById(R.id.background_gambiarra)
        lottie = findViewById(R.id.lottie)
        imagePokemon = findViewById(R.id.image_pokemon)

        //floating actioon button
        fab = findViewById(R.id.floating_action_button)
        //texts
        txtNamePokemon = findViewById(R.id.name_pokemon)
        txtPhrase = findViewById(R.id.frasepokemon)
        txtDemagePokemon = findViewById(R.id.demage_int)
        txtDefensePokemon = findViewById(R.id.defense_int)
        txtLifePokemon = findViewById(R.id.life_int)
        txtHeightPokemon = findViewById(R.id.height_int)
        txtWeightPokemon = findViewById(R.id.weight_int)
        txtBaseXpPokemon = findViewById(R.id.base_xp_int)

        //progessBars
        progressDemagePokemon = findViewById(R.id.demage_bar)
        progressDefensePokemon = findViewById(R.id.defense_bar)
        progressLifePokemon = findViewById(R.id.life_bar)
        progressHeightPokemon = findViewById(R.id.height_bar)
        progressWeightPokemon = findViewById(R.id.weight_bar)
        progressBaseXpPokemon = findViewById(R.id.base_xp_bar)


    }

    fun setValuesLayout(pokemon: PokemonResultModel) {
        txtNamePokemon?.text = pokemon.name
        txtPhrase?.text = " esse é o ${pokemon.name}"
        setLayout(0)
        Picasso.get().load(pokemon.image)
            .into(imagePokemon, object : Callback {
                override fun onSuccess() {
                    loadPalette()
                    var s = ""
                }

                override fun onError(e: Exception?) {
                }
            })
        val demage = rand(0, 1000)
        txtDemagePokemon?.text = "${demage} ATK"
        progressAnimator(progressDemagePokemon, demage)

        val defense = rand(0, 1000)
        txtDefensePokemon?.text = "${defense} DEF"
        progressAnimator(progressDefensePokemon, defense)

        val life = rand(0, 1000)
        txtLifePokemon?.text = "${life} HP"
        progressAnimator(progressLifePokemon, life)

        val height = pokemon.height.toString() + "0"
        txtHeightPokemon?.text = "${height} Cm"
        progressAnimator(progressHeightPokemon, height.toInt())

        val weight = pokemon.weight.toString()
        txtWeightPokemon?.text = "${weight} Kg"
        progressAnimator(progressWeightPokemon, weight.toInt())

        val basexp = pokemon.base_experience.toString()
        txtBaseXpPokemon?.text = "${basexp} Xp"
        progressAnimator(progressBaseXpPokemon, basexp.toInt())
    }

    private fun rand(from: Int, to: Int): Int {
        val random = Random()
        return random.nextInt(to - from) + from // from(incluso) e to(excluso)
    }

    private fun loadPalette() {
        val drawable: BitmapDrawable = imagePokemon?.drawable as BitmapDrawable
        val bitMap: Bitmap = drawable.bitmap

        val builder: Palette.Builder = Palette.Builder(bitMap)
        builder.generate { palette ->
            val lightMuted: Palette.Swatch? = palette?.lightMutedSwatch
            val muted: Palette.Swatch? = palette?.mutedSwatch
            if (lightMuted != null) {
                background?.background?.setTint(lightMuted.rgb)
                txtNamePokemon?.setTextColor(lightMuted.titleTextColor)
            } else if (muted != null) {
                background?.background?.setTint(muted.rgb)
                txtNamePokemon?.setTextColor(muted.titleTextColor)
            }
            setLayout(1)
        }

    }

    private fun setLayout(int: Int) {
        if (int == 1) {
            txtNamePokemon?.visibility = View.VISIBLE
            imagePokemon?.visibility = View.VISIBLE
            lottie?.visibility = View.GONE
        } else {
            txtNamePokemon?.visibility = View.GONE
            imagePokemon?.visibility = View.GONE
            lottie?.visibility = View.VISIBLE
        }
    }

    private fun progressAnimator(pb: ProgressBar?, info: Int) {
        pb?.max = 1000
        ObjectAnimator.ofInt(pb, "progress", info).setDuration(2000).start()
    }


}