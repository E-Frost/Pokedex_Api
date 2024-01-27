package com.example.pokedex.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.di.PokedexModule
import com.example.pokedex.data.repositories.api.PokemonService
import com.example.pokedex.domain.usecases.GetPokemonFromApi
import com.example.pokedex.domain.usecases.GetPokemonListFromApi
import com.example.pokedex.domain.usecases.GetPokemonOtherFormsFromApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PokedexViewModel @Inject constructor( val getPokemonFromApi: GetPokemonFromApi) :ViewModel() {
    val POKEMON_LIST_LIMIT = 1008
    val OFFSET = 500

    private val _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon> = _pokemon

    private var _pokemonList = MutableLiveData<List<PokemonListItem>>()
    var pokemonList: LiveData<List<PokemonListItem>> = _pokemonList

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



    private fun loadPokemonList() {
        viewModelScope.launch {
            val pokemonList = getPokemonFromApi.fetchPokemonList(POKEMON_LIST_LIMIT)
            if (!pokemonList.isNullOrEmpty()) {
                _pokemonList.value = pokemonList
                _pokemonIds.value = (1..pokemonList.size).toList()
            }
        }
    }

    private fun loadPokemonListOtherForms() {
        val otherFormsFirstElement = POKEMON_LIST_LIMIT + 1
        viewModelScope.launch {
            val pokemonListOtherForms = getPokemonFromApi.fetchPokemonListOtherForms(OFFSET, POKEMON_LIST_LIMIT)
            if (!pokemonListOtherForms.isNullOrEmpty()) {
                _pokemonListOtherForms.value = pokemonListOtherForms

                val pokemonIdsOtherForms = (otherFormsFirstElement until otherFormsFirstElement + pokemonListOtherForms.size).toList()
                _pokemonIdsotherForms.value = pokemonIdsOtherForms
            }
        }
    }

    fun cargarPokemon(pokemonName: String) {
        viewModelScope.launch {
            val pokemonFromApi = getPokemonFromApi.cargarPokemonDesdeApi(pokemonName)

            if (pokemonFromApi != null) {
                _pokemon.postValue(pokemonFromApi)
            } else {
                val pokemonFromJson = withContext(Dispatchers.IO) {
//                    repositorio.cargarDatosDesdeJSON(pokemonName)
                }

//                _pokemon.postValue(pokemonFromJson)
            }
        }
    }
}