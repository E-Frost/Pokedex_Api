package com.example.pokedex.domain.repositories

import com.example.pokedex.domain.models.Pokemon
interface IPokemonRepositoryLocal {
    fun cargarDatosDesdeJSON(pokemonName: String): Pokemon?

}