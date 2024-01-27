package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.domain.repositories.PokemonRepositoryApi
import javax.inject.Inject

class GetPokemonListFromApi @Inject constructor(private val repositoryApi: PokemonRepositoryApi) {
    suspend fun fetchPokemonList(POKEMON_LIST_LIMIT: Int): List<PokemonListItem>?{
        return repositoryApi.fetchPokemonList(POKEMON_LIST_LIMIT)
    }
}