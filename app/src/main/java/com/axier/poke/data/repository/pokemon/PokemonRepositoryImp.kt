package com.axier.poke.data.repository.pokemon

import android.content.Context
import com.axier.poke.common.mapToEntity
import com.axier.poke.data.api.ServiceGenerator
import com.axier.poke.data.api.pokemon.PokemonApi
import com.axier.poke.data.entities.pokemon.PokemonEntity
import com.axier.poke.data.repository.BaseRepositoryImpl

class PokemonRepositoryImp constructor(applicationContext: Context): BaseRepositoryImpl<PokemonEntity.Pokemon>(), PokemonRepository {

    override fun getPokemon(name: String?): PokemonEntity.Pokemon? {
        val pokemonService = ServiceGenerator.createService(PokemonApi::class.java)
        val response = pokemonService.getPokemon(name).execute()
        return if (response.isSuccessful) {
            response.body()?.mapToEntity()
        } else {
            null
        }
    }

}