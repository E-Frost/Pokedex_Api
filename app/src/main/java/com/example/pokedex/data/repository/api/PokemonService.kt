package com.example.pokedex.data.repository.api

import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.model.PokemonListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokemonService {

    @GET("pokemon/")
    suspend fun getAllPokemon(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<PokemonListResponse>

    @GET("pokemon/{dexNumOrName}/")
    fun getPokemonByDexNumOrName(@Path("dexNumOrName") dexNumOrName: String?): Call<Pokemon?>?

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    )

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int
    )

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}