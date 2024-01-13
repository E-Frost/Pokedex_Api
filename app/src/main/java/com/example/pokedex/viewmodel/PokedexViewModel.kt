package com.example.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.model.PokemonListItem
import com.example.pokedex.data.repository.Repositorio
import com.example.pokedex.data.repository.api.PokemonService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PokedexViewModel(application: Application) : AndroidViewModel(application) {
    val repositorio = Repositorio(application)
    private val _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon> = _pokemon

    private var _pokemonList = MutableLiveData<List<PokemonListItem>>()
    var pokemonList: LiveData<List<PokemonListItem>> = _pokemonList
    val POKEMON_LIST_LIMIT = 1008

    private var _pokemonIds = MutableLiveData<List<Int>>()
    var pokemonIds: LiveData<List<Int>> = _pokemonIds

    init {
        loadPokemonList()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(PokemonService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pokemonService = retrofit.create(PokemonService::class.java)

    private suspend fun fetchPokemonList(): List<PokemonListItem>? {
        return withContext(Dispatchers.IO) {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(PokemonService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

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

    private fun loadPokemonList() {
        viewModelScope.launch {
            val pokemonList = fetchPokemonList()
            if (!pokemonList.isNullOrEmpty()) {
                _pokemonList.value = pokemonList
                _pokemonIds.value = (1..pokemonList.size).toList()
            }
        }
    }

    fun cargarPokemon(pokemonName: String) {
        viewModelScope.launch {
            val pokemonFromApi = repositorio.cargarPokemonDesdeApi(pokemonName)

            if (pokemonFromApi != null) {
                _pokemon.postValue(pokemonFromApi)
            } else {
                val pokemonFromJson = withContext(Dispatchers.IO) {
                    repositorio.cargarDatosDesdeJSON(pokemonName)
                }

                _pokemon.postValue(pokemonFromJson)
            }
        }
    }
}