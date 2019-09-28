package com.axier.poke.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axier.poke.R
import com.axier.poke.common.prettify
import com.axier.poke.data.entities.pokemon.PokemonEntity
import java.util.*

class PokemonAdapter (private val elements: LinkedList<PokemonEntity.PokemonResult>, val context: Context, var callback: OnItemClickListener)
    : RecyclerView.Adapter<PokemonAdapter.ViewHolder>(), Filterable {

    private var elementsFiltered: LinkedList<PokemonEntity.PokemonResult> = elements

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_pokemon, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = elementsFiltered[position]
        holder.rootLayout?.setOnClickListener {
            callback.onItemClick(pokemon)
        }
        holder.pokemonName?.text = pokemon.name.prettify()
    }

    override fun getItemCount() = elementsFiltered.size

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

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {

                elementsFiltered = results.values as LinkedList<PokemonEntity.PokemonResult>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                var constraint = constraint
                val results = Filter.FilterResults()        // Holds the results of a filtering operation in values
                val filteredArrList = LinkedList<PokemonEntity.PokemonResult>()
                // If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                // else does the Filtering and returns FilteredArrList(Filtered)
                if (constraint == null || constraint.isEmpty()) {
                    // set the Original result to return
                    results.count = elements.size
                    results.values = elements
                } else {
                    constraint = constraint.toString().toLowerCase(Locale.getDefault())
                    for (i in 0 until elements.size) {
                        val item = elements[i]
                        if (item.name.toLowerCase(Locale.getDefault()).contains(constraint)) {
                            filteredArrList.add(item)
                        }
                    }
                    // set the Filtered result to return
                    results.count = filteredArrList.size
                    results.values = filteredArrList
                }
                return results
            }
        }
    }
}