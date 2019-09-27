package com.axier.poke.view.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axier.poke.R
import com.axier.poke.common.mapToEntity
import com.axier.poke.data.api.ServiceGenerator
import com.axier.poke.data.api.pokemon.PokemonApi
import com.axier.poke.data.entities.pokemon.PokemonEntity
import com.axier.poke.view.adapter.PokemonAdapter
import org.jetbrains.anko.doAsync
import java.util.*

class MainActivity : AppCompatActivity() {

    private var adapter: PokemonAdapter? = null
    private lateinit var pokemonList: RecyclerView
    private var offset: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        pokemonList = findViewById(R.id.pokemon_list)
        pokemonList.layoutManager = layoutManager
        pokemonList.setHasFixedSize(true)

        adapter = PokemonAdapter(LinkedList(), this, object : PokemonAdapter.OnItemClickListener {
            override fun onItemClick(pokemonResult: PokemonEntity.PokemonResult) {

            }
        })
        pokemonList.adapter = adapter

        doGetPokemonRequest(offset)
    }

    private fun doGetPokemonRequest(offset: Int) {
        doAsync {
            val pokemonService = ServiceGenerator.createService(PokemonApi::class.java)
            val response = pokemonService.getPokemons(0).execute()
            if (response.isSuccessful) {
                val pokemons = response.body()
                pokemons?.results?.let { it ->
                    loadAdapterData(it.map { it.mapToEntity() })
                }
            } else {
                showToast(R.string.http_generic_error)
            }
        }
    }

    private fun loadAdapterData(pokemons: List<PokemonEntity.PokemonResult>) {
        runOnUiThread {
            adapter?.addAll(pokemons = pokemons)
        }
    }

    private fun showToast(stringRes: Int) {
        runOnUiThread {
            Toast.makeText(
                this@MainActivity,
                stringRes,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
