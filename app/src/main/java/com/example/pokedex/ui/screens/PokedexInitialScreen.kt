package com.example.pokedex.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pokedex.ui.viewmodels.PokedexViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexInitialScreen(navController: NavHostController, viewModel: PokedexViewModel) {

    val viewModelScope = rememberCoroutineScope()
    var pokemonName by remember { mutableStateOf("") }
    var pokemonID by remember { mutableStateOf("") }


    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.large)
                    .clickable {
                        navController.navigate("listaApi")

                    }
                    .background(MaterialTheme.colorScheme.primary)
            ) {

            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pokemonName,
                onValueChange = {
                    pokemonName = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Buscar por nombre") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModelScope.launch {
                                try {
                                    val call =
                                        viewModel.pokemonService.getPokemonByName(pokemonName)

                                    if (call.isSuccessful) {
                                        val pokemonFromApi = call.body()

                                        if (pokemonFromApi != null) {
                                            viewModel.cargarPokemon(pokemonFromApi.name)
                                            navController.navigate("pokedex")
                                        } else {
                                            Log.e(
                                                "PokedexApp",
                                                "Error en la respuesta de la API: Pokemon nulo"
                                            )
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
                        }
                    )

                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pokemonID,
                onValueChange = {
                    pokemonID = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Buscar por ID") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModelScope.launch {
                                try {
                                    val call =
                                        viewModel.pokemonService.getPokemonById(pokemonID)

                                    if (call.isSuccessful) {
                                        val pokemonFromApi = call.body()

                                        if (pokemonFromApi != null) {
                                            viewModel.cargarPokemon(pokemonFromApi.name)
                                            navController.navigate("pokedex")
                                        } else {
                                            Log.e(
                                                "PokedexApp",
                                                "Error en la respuesta de la API: Pokemon nulo"
                                            )
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
                        }
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        navController.navigate("listaApiOtrasFormas")
                    }
                    .background(MaterialTheme.colorScheme.secondary)
            ) {

            }
        }
    }
}



