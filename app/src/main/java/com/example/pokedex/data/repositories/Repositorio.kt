package com.example.pokedex.data.repositories

import android.app.Application
import android.util.Log
import com.example.pokedex.di.PokedexModule
import com.example.pokedex.data.repositories.api.PokemonService
import com.example.pokedex.domain.models.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

class Repositorio @Inject constructor(
    private val application: Application,
    private val pokemonService: PokemonService
) {

    fun cargarDatosDesdeJSON( pokemonName:String): Pokemon? {

        var retornoPokemon: Pokemon? = null
        try {

            val fileName = "$pokemonName.json"
            val assetManager = application.assets
            val inputStream: InputStream = assetManager.open(fileName)
            val json =
                inputStream.bufferedReader(Charset.defaultCharset()).use { it.readText() }

            val gson = PokedexModule.provideGson()
            val pokemon = gson.fromJson(json, Pokemon::class.java)

            retornoPokemon = pokemon
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("PokedexViewModel", "Error loading JSON: ${e.message}")
        }

        return retornoPokemon
    }
    suspend fun cargarPokemonDesdeApi(pokemonName: String): Pokemon? {
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
}