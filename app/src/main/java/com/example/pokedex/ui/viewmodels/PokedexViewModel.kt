package com.example.pokedex.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.di.PokedexModule
import com.example.pokedex.data.sources.remote.api.PokemonService
import com.example.pokedex.domain.usecases.GetPokemonFromApiUseCase
import com.example.pokedex.domain.usecases.GetPokemonFromJsonUseCase
import com.example.pokedex.domain.usecases.GetPokemonListFromApiUseCase
import com.example.pokedex.domain.usecases.GetPokemonOtherFormsFromApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PokedexViewModel @Inject constructor(val getPokemonFromApiUseCase: GetPokemonFromApiUseCase, val getPokemonListFromApiUseCase: GetPokemonListFromApiUseCase, val getPokemonOtherFormsFromApiUseCase: GetPokemonOtherFormsFromApiUseCase, val getPokemonFromJsonUseCase: GetPokemonFromJsonUseCase) :ViewModel() {
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

    private val _megaForm = MutableLiveData<Boolean>()
    val megaForm: LiveData<Boolean> get() = _megaForm

    init {
        loadPokemonList()
        loadPokemonListOtherForms()
    }

    val retrofit = PokedexModule.providesRetrofit()
    val pokemonService = retrofit.create(PokemonService::class.java)



    private fun loadPokemonList() {
        viewModelScope.launch {
            val pokemonList = getPokemonListFromApiUseCase.fetchPokemonList(POKEMON_LIST_LIMIT)
            if (!pokemonList.isNullOrEmpty()) {
                _pokemonList.value = pokemonList
                _pokemonIds.value = (1..pokemonList.size).toList()
            }
        }
    }

    private fun loadPokemonListOtherForms() {
        val otherFormsFirstElement = POKEMON_LIST_LIMIT + 1
        viewModelScope.launch {
            val pokemonListOtherForms = getPokemonOtherFormsFromApiUseCase.fetchPokemonListOtherForms(OFFSET, POKEMON_LIST_LIMIT)
            if (!pokemonListOtherForms.isNullOrEmpty()) {
                _pokemonListOtherForms.value = pokemonListOtherForms

                val pokemonIdsOtherForms = (otherFormsFirstElement until otherFormsFirstElement + pokemonListOtherForms.size).toList()
                _pokemonIdsotherForms.value = pokemonIdsOtherForms
            }
        }
    }

    fun cargarPokemon(pokemonName: String) {
        viewModelScope.launch {
            val pokemonFromApi = getPokemonFromApiUseCase.cargarPokemonDesdeApi(pokemonName)

            if (pokemonFromApi != null) {
                _pokemon.postValue(pokemonFromApi)
            } else {
                val pokemonFromJson = withContext(Dispatchers.IO) {
                   getPokemonFromJsonUseCase.cargarDatosDesdeJSON(pokemonName)
                }

                _pokemon.postValue(pokemonFromJson)
            }
        }
    }
}