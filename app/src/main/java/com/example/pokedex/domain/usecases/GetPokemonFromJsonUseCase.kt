package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repositories.IPokemonRepositoryLocal
import javax.inject.Inject

class GetPokemonFromJsonUseCase @Inject constructor(val repositoryLocal: IPokemonRepositoryLocal) {
    fun cargarDatosDesdeJSON(pokemonName: String): Pokemon?{
        return repositoryLocal.cargarDatosDesdeJSON(pokemonName)
    }
}