package com.axier.poke.data.entities.pokemon

import com.axier.poke.data.entities.BaseEntity

sealed class PokemonEntity : BaseEntity() {

    data class PokemonResult (
        val name: String
    ): PokemonEntity() {
        var url: String? = null
    }
}