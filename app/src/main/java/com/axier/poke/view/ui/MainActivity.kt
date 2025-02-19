package com.axier.poke.view.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axier.poke.R
import com.axier.poke.data.entities.pokemon.PokemonEntity
import com.axier.poke.data.repository.pokemon.PokemonListRepositoryImp
import com.axier.poke.view.BaseActivity
import com.axier.poke.view.adapter.PokemonAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), BaseActivity {

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
            override fun onItemClick(pokemon: PokemonEntity.PokemonResult) {
                val intent = Intent(this@MainActivity, PokemonDetailActivity::class.java)
                intent.putExtra("pokemon_name", pokemon.name)
                startActivity(intent)
            }
        })
        pokemonList.adapter = adapter

        doGetPokemonRequest(offset)
    }

    private fun doGetPokemonRequest(offset: Int) {
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            val pokemons = PokemonListRepositoryImp(applicationContext).getPokemons(0)
            launch(Dispatchers.Main) {
                when (pokemons) {
                    null -> {
                        showToast(R.string.http_generic_error)
                    }

                    else -> {
                        pokemons.results?.let { it ->
                            loadAdapterData(it)
                        }
                    }
                }
            }
        }
    }

    private fun loadAdapterData(pokemons: List<PokemonEntity.PokemonResult>) {
        adapter?.addAll(pokemons = pokemons)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.pokemon_list_menu, menu)
        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false) // Do not iconify the widget; expand it by default

        val queryTextListener = object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String): Boolean {
                adapter?.filter?.filter(newText)
                adapter?.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
        searchView.setOnCloseListener {
            val imm = getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchView.windowToken, 0)
            false
        }
        return true
    }

    private fun showToast(stringRes: Int) {
        Toast.makeText(
            this@MainActivity,
            stringRes,
            Toast.LENGTH_LONG
        ).show()
    }
}
