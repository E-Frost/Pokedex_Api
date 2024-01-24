package com.example.pokedex.data.repository

import android.content.Context
import android.util.Log
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.repository.api.PokemonService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class Repositorio(val context: Context) {

    fun cargarDatosDesdeJSON( pokemonName:String): Pokemon? {

        var retornoPokemon: Pokemon? = null
        try {

            val fileName = "$pokemonName.json"
            val assetManager = context.assets
            val inputStream: InputStream = assetManager.open(fileName)
            val json =
                inputStream.bufferedReader(Charset.defaultCharset()).use { it.readText() }

            val gson = Gson()
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
                val retrofit = Retrofit.Builder()
                    .baseUrl(PokemonService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val pokemonService = retrofit.create(PokemonService::class.java)
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