package com.axier.poke.data.api.pokemon

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Query

interface PokemonApi {

    @HEAD("pokemon/")
    fun headPokemons(): Call<Void>

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

    }
}