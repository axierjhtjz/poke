package com.axier.poke.view.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import coil.transform.CircleCropTransformation
import com.axier.poke.R
import com.axier.poke.common.prettify
import com.axier.poke.data.entities.pokemon.PokemonEntity
import com.axier.poke.data.repository.pokemon.PokemonRepositoryImp
import com.axier.poke.view.BaseActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick

class PokemonDetailActivity : AppCompatActivity(), BaseActivity {

    private lateinit var pokemonName: String
    private lateinit var pokemonNameTv: TextView
    private lateinit var pokemonImg: ImageView
    private lateinit var back: ImageView
    private lateinit var nextImage: ImageButton
    private lateinit var pokemonHeight: TextView
    private lateinit var pokemonWeight: TextView
    private lateinit var pokemonBaseExp: TextView
    private lateinit var pokemonTypes: TextView
    private lateinit var pokemonMoves: TextView
    private var spritesIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        intent.extras.let {
            pokemonName = it.getString("pokemon_name", "")
        }

        findUIElements()

        back.onClick { finish() }

        doGetPokemonRequest(pokemonName)

    }

    private fun findUIElements() {
        back = findViewById(R.id.back)
        pokemonNameTv = findViewById(R.id.pokemon_name)
        pokemonImg = findViewById(R.id.pokemon_image)
        nextImage = findViewById(R.id.pokemon_next_image)
        pokemonHeight = findViewById(R.id.pokemon_height)
        pokemonWeight = findViewById(R.id.pokemon_weight)
        pokemonBaseExp = findViewById(R.id.pokemon_base_exp)
        pokemonTypes = findViewById(R.id.pokemon_types)
        pokemonMoves = findViewById(R.id.pokemon_moves)
    }

    private fun doGetPokemonRequest(pokemonName: String?) {
        doAsync {
            when (val pokemon = PokemonRepositoryImp(applicationContext).getPokemon(pokemonName)) {
                null -> showToast(R.string.http_generic_error)
                else -> {
                    runOnUiThread { loadPokemonUIData(pokemon) }
                }
            }
        }
    }

    private fun loadPokemonUIData(pokemon: PokemonEntity.Pokemon) {
        pokemonNameTv.text = pokemon.name.prettify()
        pokemonHeight.text = getString(R.string.pokemon_height, pokemon.height)
        pokemonWeight.text = getString(R.string.pokemon_weight, pokemon.weight)
        pokemonBaseExp.text = getString(R.string.pokemon_base_ext, pokemon.baseExperience)
        pokemonTypes.text = getString(R.string.pokemon_types, pokemon.types?.map { it.type?.name }?.joinToString())

        val moves = pokemon.moves?.map { it.move.name.replace("-", " ") }
        moves?.forEach {
            pokemonMoves.append(it)
            pokemonMoves.append(System.getProperty("line.separator"))
        }

        var sprites = loadSpritesToList(pokemon.sprites)
        sprites = sprites.filterNot { it == null }
        if (sprites.isNotEmpty()) {
            loadPokemonImage(sprites[0])
        }
        nextImage.onClick {
            if (spritesIndex < sprites.size - 1) {
                spritesIndex++
            } else {
                spritesIndex = 0
            }
            loadPokemonImage(sprites[spritesIndex])
        }
    }

    private fun loadSpritesToList(sprites: PokemonEntity.PokemonSprite?): List<String?> {
        return listOf(
            sprites?.backDefault,
            sprites?.backFemale,
            sprites?.backShiny,
            sprites?.backShinyFemale,
            sprites?.frontDefault,
            sprites?.frontFemale,
            sprites?.frontShiny,
            sprites?.frontShinyFemale
        )
    }

    private fun loadPokemonImage(url: String?) {
        pokemonImg.load(url) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    private fun showToast(stringRes: Int) {
        runOnUiThread {
            Toast.makeText(
                this@PokemonDetailActivity,
                stringRes,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
