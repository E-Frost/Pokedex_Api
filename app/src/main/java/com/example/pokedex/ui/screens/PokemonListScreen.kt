package com.example.pokedex.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedex.ui.viewmodels.PokedexViewModel
import kotlinx.coroutines.launch
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(navController: NavHostController, viewModel: PokedexViewModel) {
    val pokemonList by viewModel.pokemonList.observeAsState(emptyList())
    val pokemonIds by viewModel.pokemonIds.observeAsState(emptyList())
    val viewModelScope = rememberCoroutineScope()

    Scaffold(
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(25.dp)
            ) {
                items(pokemonList) { pokemon ->
                    val index = pokemonList.indexOf(pokemon)
                    val currentPokemonId = pokemonIds.getOrNull(index)

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(25.dp)
                            .clickable {

                                viewModelScope.launch {
                                    try {
                                        val call =
                                            viewModel.pokemonService.getPokemonByName(pokemon.name)

                                        if (call.isSuccessful) {
                                            val pokemonFromApi = call.body()

                                            if (pokemonFromApi != null) {
                                                viewModel.cargarPokemon(pokemonFromApi.name)
                                                navController.navigate("pokedex")
                                            }
                                        } else {
                                            Log.e(
                                                "PokedexApp",
                                                "Error en la respuesta de la API: ${call.message()}"
                                            )
                                        }
                                    } catch (e: Exception) {
                                        Log.e(
                                            "PokedexApp",
                                            "Error al realizar la solicitud a la API: ${e.message}"
                                        )
                                    }
                                }
                            },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(30.dp)
                        ) {
                            if (currentPokemonId != null) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "#$currentPokemonId",
                                        style = TextStyle(
                                            fontSize = 23.sp,
                                            color = Color.White,
                                            textAlign = TextAlign.Left
                                        )
                                    )

                                    Text(
                                        text = pokemon.name.replaceFirstChar {
                                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                        },
                                        style = TextStyle(
                                            fontSize = 23.sp,
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack()},
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}