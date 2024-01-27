package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.domain.repositories.PokemonRepositoryApi
import javax.inject.Inject

class GetPokemonFromApi @Inject constructor(private val repositoryApi: PokemonRepositoryApi) {
    suspend fun cargarPokemonDesdeApi(pokemonName: String): Pokemon?{
        return repositoryApi.cargarPokemonDesdeApi(pokemonName)
    }

    suspend fun fetchPokemonListOtherForms(offset: Int, POKEMON_LIST_LIMIT: Int): List<PokemonListItem>?{
        return repositoryApi.fetchPokemonListOtherForms(offset, POKEMON_LIST_LIMIT)
    }

    suspend fun fetchPokemonList(POKEMON_LIST_LIMIT: Int): List<PokemonListItem>?{
        return repositoryApi.fetchPokemonList(POKEMON_LIST_LIMIT)
    }

}