package com.axier.poke.view.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.axier.poke.R
import com.axier.poke.data.api.ServiceGenerator
import com.axier.poke.data.api.pokemon.PokemonApi
import com.axier.poke.view.BaseActivity
import org.jetbrains.anko.doAsync

class PokemonDetailActivity : AppCompatActivity(), BaseActivity {

    private lateinit var pokemonName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        intent.extras.let {
            pokemonName = it.getString("pokemon_name", "")
        }

        doGetPokemonRequest(pokemonName)

    }

    private fun doGetPokemonRequest(pokemonName: String?) {
        doAsync {
            val pokemonService = ServiceGenerator.createService(PokemonApi::class.java)
            val response = pokemonService.getPokemon(pokemonName).execute()
            if (response.isSuccessful) {
                val pokemon = response.body()
                pokemon?.let { it ->
                    loadPokemonUIData(pokemon)
                }
            } else {
                showToast(R.string.http_generic_error)
            }
        }
    }

    private fun loadPokemonUIData(pokemon: PokemonApi.Dto.Pokemon) {

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
