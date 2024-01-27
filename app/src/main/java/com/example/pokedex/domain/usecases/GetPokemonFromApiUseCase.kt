package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repositories.IPokemonRepositoryApi
import javax.inject.Inject

class GetPokemonFromApiUseCase @Inject constructor(private val repositoryApi: IPokemonRepositoryApi) {
    suspend fun cargarPokemonDesdeApi(pokemonName: String): Pokemon?{
        return repositoryApi.cargarPokemonDesdeApi(pokemonName)
    }
}