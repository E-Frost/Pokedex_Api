package com.example.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.model.PokemonListItem
import com.example.pokedex.data.repository.Repositorio
import com.example.pokedex.data.repository.api.PokedexModule
import com.example.pokedex.data.repository.api.PokemonService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


@HiltViewModel
class PokedexViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    val repositorio = Repositorio(PokedexModule.provideContext(application),PokedexModule.providePokemonService())
    private val _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon> = _pokemon

    private var _pokemonList = MutableLiveData<List<PokemonListItem>>()
    var pokemonList: LiveData<List<PokemonListItem>> = _pokemonList
    val POKEMON_LIST_LIMIT = 1008
    val OFFSET = 500

    private var _pokemonIds = MutableLiveData<List<Int>>()
    var pokemonIds: LiveData<List<Int>> = _pokemonIds

    private var _pokemonListOtherForms = MutableLiveData<List<PokemonListItem>>()
    var pokemonListOtherForms: LiveData<List<PokemonListItem>> = _pokemonListOtherForms

    private var _pokemonIdsotherForms = MutableLiveData<List<Int>>()
    var pokemonIdsOtherForms: LiveData<List<Int>> = _pokemonIdsotherForms

    init {
        loadPokemonList()
        loadPokemonListOtherForms()
    }

    val retrofit = PokedexModule.providesRetrofit()
    val pokemonService = retrofit.create(PokemonService::class.java)

    private suspend fun fetchPokemonList(): List<PokemonListItem>? {
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

    private fun loadPokemonList() {
        viewModelScope.launch {
            val pokemonList = fetchPokemonList()
            if (!pokemonList.isNullOrEmpty()) {
                _pokemonList.value = pokemonList
                _pokemonIds.value = (1..pokemonList.size).toList()
            }
        }
    }

    private fun loadPokemonListOtherForms() {
        val otherFormsFirstElement = POKEMON_LIST_LIMIT + 1
        viewModelScope.launch {
            val pokemonListOtherForms = fetchPokemonListOtherForms(OFFSET)
            if (!pokemonListOtherForms.isNullOrEmpty()) {
                _pokemonListOtherForms.value = pokemonListOtherForms

                val pokemonIdsOtherForms = (otherFormsFirstElement until otherFormsFirstElement + pokemonListOtherForms.size).toList()
                _pokemonIdsotherForms.value = pokemonIdsOtherForms
            }
        }
    }

    private suspend fun fetchPokemonListOtherForms(offset: Int): List<PokemonListItem>? {
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