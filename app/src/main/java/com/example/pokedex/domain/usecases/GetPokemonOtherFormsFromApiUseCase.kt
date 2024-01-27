package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.domain.repositories.IPokemonRepositoryApi
import javax.inject.Inject

class GetPokemonOtherFormsFromApiUseCase @Inject constructor(private val repositoryApi: IPokemonRepositoryApi) {
    suspend fun fetchPokemonListOtherForms(offset: Int, POKEMON_LIST_LIMIT: Int): List<PokemonListItem>?{
        return repositoryApi.fetchPokemonListOtherForms(offset, POKEMON_LIST_LIMIT)
    }
}