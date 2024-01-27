package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repositories.PokemonRepositoryApi
import javax.inject.Inject

class GetPokemonFromApi @Inject constructor(private val repositoryApi: PokemonRepositoryApi) {
    suspend fun cargarPokemonDesdeApi(pokemonName: String): Pokemon?{
        return repositoryApi.cargarPokemonDesdeApi(pokemonName)
    }
}