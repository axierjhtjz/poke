package com.axier.poke.common

import com.axier.poke.data.api.pokemon.PokemonApi
import com.axier.poke.data.entities.pokemon.PokemonEntity

fun PokemonApi.Dto.PokemonResult.mapToEntity(): PokemonEntity.PokemonResult {
    val pokemonResult = PokemonEntity.PokemonResult(name = name)
    pokemonResult.url = url
    return pokemonResult
}

fun PokemonEntity.PokemonResult.mapEntityToDTO() = PokemonApi.Dto.PokemonResult(
    name = name,
    url = url
)
