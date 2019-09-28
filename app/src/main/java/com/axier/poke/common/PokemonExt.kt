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

fun PokemonApi.Dto.PokemonList.mapToEntity() = PokemonEntity.PokemonList(
    count = count,
    next = next,
    previous = previous,
    results = results?.map { it.mapToEntity() }
)

fun PokemonEntity.PokemonList.mapEntityToDTO() = PokemonApi.Dto.PokemonList(
    count = count,
    next = next,
    previous = previous,
    results = results?.map { it.mapEntityToDTO() }
)

fun PokemonApi.Dto.Pokemon.mapToEntity(): PokemonEntity.Pokemon {
    val pokemon = PokemonEntity.Pokemon(id = id)
    pokemon.name = name
    pokemon.baseExperience = baseExperience
    pokemon.height = height
    pokemon.weight = weight
    pokemon.types = types?.map { it.mapToEntity()}
    pokemon.sprites = sprites?.mapToEntity()
    pokemon.moves = moves?.map { it.mapToEntity() }
    return pokemon
}

fun PokemonEntity.Pokemon.mapEntityToDTO() = PokemonApi.Dto.Pokemon (
    id = id,
    name = name,
    baseExperience = baseExperience,
    height = height,
    weight = weight,
    types = types?.map { it.mapEntityToDTO() },
    sprites = sprites?.mapEntityToDTO(),
    moves = moves?.map { it.mapEntityToDTO() }
)

fun PokemonApi.Dto.PokemonType.mapToEntity() = PokemonEntity.PokemonType(
    slot = slot,
    type = type?.mapToEntity()
)

fun PokemonEntity.PokemonType.mapEntityToDTO() = PokemonApi.Dto.PokemonType(
    slot = slot,
    type = type?.mapEntityToDTO()
)

fun PokemonApi.Dto.Type.mapToEntity(): PokemonEntity.Type {
    val type = PokemonEntity.Type(name = name)
    type.url = url
    return type
}

fun PokemonEntity.Type.mapEntityToDTO() = PokemonApi.Dto.Type(
    name = name,
    url = url
)

fun PokemonApi.Dto.PokemonSprite.mapToEntity()= PokemonEntity.PokemonSprite(
    backDefault = backDefault,
    backFemale = backFemale,
    backShiny = backShiny,
    backShinyFemale = backShinyFemale,
    frontDefault = frontDefault,
    frontFemale = frontFemale,
    frontShiny = frontShiny,
    frontShinyFemale = frontShinyFemale
)

fun PokemonEntity.PokemonSprite.mapEntityToDTO() = PokemonApi.Dto.PokemonSprite(
    backDefault = backDefault,
    backFemale = backFemale,
    backShiny = backShiny,
    backShinyFemale = backShinyFemale,
    frontDefault = frontDefault,
    frontFemale = frontFemale,
    frontShiny = frontShiny,
    frontShinyFemale = frontShinyFemale
)

fun PokemonApi.Dto.PokemonMove.mapToEntity()= PokemonEntity.PokemonMove(
    move = move.mapToEntity()
)

fun PokemonEntity.PokemonMove.mapEntityToDTO() = PokemonApi.Dto.PokemonMove(
    move = move.mapEntityToDTO()
)

fun PokemonApi.Dto.Move.mapToEntity()= PokemonEntity.Move(
    name = name
)

fun PokemonEntity.Move.mapEntityToDTO() = PokemonApi.Dto.Move(
    name = name
)