package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.domain.repositories.IPokemonRepositoryApi
import javax.inject.Inject

class GetPokemonListFromApiUseCase @Inject constructor(private val repositoryApi: IPokemonRepositoryApi) {
    suspend fun fetchPokemonList(POKEMON_LIST_LIMIT: Int): List<PokemonListItem>?{
        return repositoryApi.fetchPokemonList(POKEMON_LIST_LIMIT)
    }
}