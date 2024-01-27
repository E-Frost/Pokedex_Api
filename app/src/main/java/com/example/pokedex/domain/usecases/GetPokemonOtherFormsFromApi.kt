package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.domain.repositories.PokemonRepositoryApi
import javax.inject.Inject

class GetPokemonOtherFormsFromApi @Inject constructor(private val repositoryApi: PokemonRepositoryApi) {
    suspend fun fetchPokemonListOtherForms(offset: Int, POKEMON_LIST_LIMIT: Int): List<PokemonListItem>?{
        return repositoryApi.fetchPokemonListOtherForms(offset, POKEMON_LIST_LIMIT)
    }
}