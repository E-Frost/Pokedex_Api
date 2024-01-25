package com.example.pokedex.data.repositories.api

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonListResponse
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

    @GET("pokemon/")
    suspend fun getAllPokemonUnusualVersions(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<PokemonListResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ): Response<Pokemon>

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: String
    ): Response<Pokemon>

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}