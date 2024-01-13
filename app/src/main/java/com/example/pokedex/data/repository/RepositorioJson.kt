package com.example.pokedex.data.repository

import android.content.Context
import android.util.Log
import com.example.pokedex.data.model.Pokemon
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class RepositorioJson(val context: Context) {

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
}