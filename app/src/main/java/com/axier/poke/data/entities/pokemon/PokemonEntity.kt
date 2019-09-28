package com.axier.poke.data.entities.pokemon

import com.axier.poke.data.entities.BaseEntity

sealed class PokemonEntity : BaseEntity() {

    data class PokemonList(
        var count: Int,
        var next: String?,
        var previous: String?,
        var results: List<PokemonResult>?
    ): PokemonEntity()

    data class PokemonResult (
        val name: String
    ): PokemonEntity() {
        var url: String? = null
    }

    data class Pokemon(
        var id: Int
    ): PokemonEntity() {
        var name: String? = null
        var baseExperience: Int? = null
        var height: Int? = null
        var weight: Int? = null
        var types: List<PokemonType>? = null
        var sprites: PokemonSprite? = null
        var moves: List<PokemonMove>? = null
    }

    data class PokemonMove(
        var move: Move
    ): PokemonEntity()

    data class Move(
        var name: String
    ): PokemonEntity()

    data class PokemonSprite(
        var backDefault: String?,
        var backFemale: String?,
        var backShiny: String?,
        var backShinyFemale: String?,
        var frontDefault: String?,
        var frontFemale: String?,
        var frontShiny: String?,
        var frontShinyFemale: String?
    ): PokemonEntity()

    data class PokemonType(
        var slot: Int,
        var type: Type?
    ): PokemonEntity()

    data class Type(
        val name: String
    ): PokemonEntity() {
        var url: String? = null
    }
}