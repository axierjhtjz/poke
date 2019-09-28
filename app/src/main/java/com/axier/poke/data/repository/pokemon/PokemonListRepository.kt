package com.axier.poke.data.repository.pokemon

import com.axier.poke.data.entities.pokemon.PokemonEntity
import com.axier.poke.data.repository.BaseRepository

interface PokemonListRepository : BaseRepository {

    /**
     * Perform
     */

    fun getPokemons(offset: Int): PokemonEntity.PokemonList?

}