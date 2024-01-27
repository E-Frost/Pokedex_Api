package com.example.pokedex.domain.repositories

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonListItem

interface IPokemonRepositoryApi {
    suspend fun cargarPokemonDesdeApi(pokemonName: String): Pokemon?
    suspend fun fetchPokemonList(POKEMON_LIST_LIMIT: Int): List<PokemonListItem>?
    suspend fun fetchPokemonListOtherForms(offset: Int, POKEMON_LIST_LIMIT: Int): List<PokemonListItem>?
}
