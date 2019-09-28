package com.axier.poke.data.api.pokemon

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @HEAD("pokemon/")
    fun headPokemons(): Call<Void>

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String?): Call<Dto.Pokemon>

    @GET("pokemon/?limit=200")
    fun getPokemons(@Query("offset") offset: Int): Call<Dto.PokemonList>

    sealed class Dto {

        data class PokemonList(
            @SerializedName("count") var count: Int,
            @SerializedName("next") var next: String?,
            @SerializedName("previous") var previous: String?,
            @SerializedName("results") var results: List<PokemonResult>?
        ): Dto()

        data class PokemonResult(
            @SerializedName("name") var name: String,
            @SerializedName("url") var url: String?
        ): Dto()

        data class Pokemon(
            @SerializedName("id") var id: Int,
            @SerializedName("name") var name: String?,
            @SerializedName("base_experience") var baseExperience: Int?,
            @SerializedName("height") var height: Int?,
            @SerializedName("weight") var weight: Int?,
            @SerializedName("types") var types: List<PokemonType>?,
            @SerializedName("sprites") var sprites: PokemonSprite?,
            @SerializedName("moves") var moves: List<PokemonMove>?
        ): Dto()

        data class PokemonMove(
            @SerializedName("move") var move: Move
        ): Dto()

        data class Move(
            @SerializedName("name") var name: String
        ): Dto()

        data class PokemonSprite(
            @SerializedName("back_default") var backDefault: String?,
            @SerializedName("back_female") var backFemale: String?,
            @SerializedName("back_shiny") var backShiny: String?,
            @SerializedName("back_shiny_female") var backShinyFemale: String?,
            @SerializedName("front_default") var frontDefault: String?,
            @SerializedName("front_female") var frontFemale: String?,
            @SerializedName("front_shiny") var frontShiny: String?,
            @SerializedName("front_shiny_female") var frontShinyFemale: String?
        ): Dto()

        data class PokemonType(
            @SerializedName("slot") var slot: Int,
            @SerializedName("type") var type: Type?
        ): Dto()

        data class Type(
            @SerializedName("name") var name: String,
            @SerializedName("url") var url: String?
        ): Dto()
    }
}