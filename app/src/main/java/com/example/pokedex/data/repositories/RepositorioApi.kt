package com.example.pokedex.data.repositories

import android.app.Application
import com.example.pokedex.data.sources.remote.api.PokemonService
import com.example.pokedex.di.PokedexModule
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.domain.repositories.PokemonRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositorioApi @Inject constructor(private val application: Application, private val pokemonService: PokemonService): PokemonRepositoryApi {
    override suspend fun cargarPokemonDesdeApi(pokemonName: String): Pokemon? {
        return withContext(Dispatchers.IO) {
            try {
                val response = pokemonService.getPokemonByName(pokemonName)

                if (response.isSuccessful) {
                    return@withContext response.body()
                } else {
                    return@withContext null
                }
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }

    override suspend fun fetchPokemonList(POKEMON_LIST_LIMIT: Int): List<PokemonListItem>? {
        return withContext(Dispatchers.IO) {
            val retrofit = PokedexModule.providesRetrofit()

            try {

                val pokemonService = retrofit.create(PokemonService::class.java)
                val response = pokemonService.getAllPokemon(limit = POKEMON_LIST_LIMIT)

                if (response.isSuccessful) {
                    return@withContext response.body()?.results
                } else {
                    return@withContext null
                }
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }

    override suspend fun fetchPokemonListOtherForms(offset: Int, POKEMON_LIST_LIMIT: Int): List<PokemonListItem>? {
        return withContext(Dispatchers.IO) {
            try {
                val retrofit = PokedexModule.providesRetrofit()

                val pokemonService = retrofit.create(PokemonService::class.java)
                val response = pokemonService.getAllPokemonUnusualVersions(
                    limit = POKEMON_LIST_LIMIT,
                    offset = offset
                )

                if (response.isSuccessful) {
                    val allResults = response.body()?.results
                    return@withContext allResults?.subList(offset, allResults.size)
                } else {
                    return@withContext null
                }
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }
}