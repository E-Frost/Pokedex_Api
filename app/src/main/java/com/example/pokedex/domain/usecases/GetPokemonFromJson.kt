package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repositories.PokemonRepositoryLocal
import javax.inject.Inject

class GetPokemonFromJson @Inject constructor(val repositoryLocal: PokemonRepositoryLocal) {
    fun cargarDatosDesdeJSON(pokemonName: String): Pokemon?{
        return repositoryLocal.cargarDatosDesdeJSON(pokemonName)
    }
}