package com.example.pokedex.domain.repositories

import com.example.pokedex.domain.models.Pokemon
interface PokemonRepositoryLocal {
    fun cargarDatosDesdeJSON(pokemonName: String): Pokemon?

}