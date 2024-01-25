package com.example.pokedex.domain.repositories

import com.example.pokedex.domain.models.Pokemon
interface PokemonRepository {
    fun cargarDatosDesdeJSON(pokemonName: String): Pokemon?

    suspend fun cargarPokemonDesdeApi(pokemonName: String): Pokemon?
}
