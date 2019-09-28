package com.axier.poke.data.repository.pokemon

import com.axier.poke.data.entities.pokemon.PokemonEntity
import com.axier.poke.data.repository.BaseRepository

interface PokemonRepository : BaseRepository {

    /**
     * Perform
     */

    fun getPokemon(name: String?): PokemonEntity.Pokemon?

}