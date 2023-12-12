package com.example.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.repository.RepositorioJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PokedexViewModel(application: Application) : AndroidViewModel(application) {
    val repositorio = RepositorioJson(application)
    private val _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon> = _pokemon

    /*
    init {
        cargarPokemon(pokemonName)
    }
     */

    fun cargarPokemon(pokemonName: String) {
        viewModelScope.launch {
            val loadedPokemon = withContext(Dispatchers.IO) {
                repositorio.cargarDatosDesdeJSON(pokemonName)
            }
            _pokemon.postValue(loadedPokemon)
        }
    }
}

