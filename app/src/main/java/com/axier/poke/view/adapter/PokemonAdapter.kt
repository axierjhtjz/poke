package com.axier.poke.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axier.poke.R
import com.axier.poke.common.prettify
import com.axier.poke.data.entities.pokemon.PokemonEntity
import java.util.*

class PokemonAdapter (private val elements: LinkedList<PokemonEntity.PokemonResult>, val context: Context, var callback: OnItemClickListener)
    : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_pokemon, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = elements[position]
        holder.rootLayout?.setOnClickListener {
            callback.onItemClick(pokemon)
        }
        holder.pokemonName?.text = pokemon.name.prettify()
    }

    override fun getItemCount() = elements.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var rootLayout: LinearLayout? = null
        var pokemonName: TextView? = null

        init {
            rootLayout = itemView.findViewById(R.id.root_layout)
            pokemonName = itemView.findViewById(R.id.pokemon_name)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(pokemon: PokemonEntity.PokemonResult)
    }

    fun addAll(pokemons: List<PokemonEntity.PokemonResult>) {
        elements.clear()
        elements.addAll(pokemons)
        notifyDataSetChanged()
    }
}