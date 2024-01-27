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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.large)
                    .clickable {
                        navController.navigate("listaApi")
                    },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),

                ) {
                Text(
                    text = "Lista Pokemons",
                    style = TextStyle(
                        fontSize = 23.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pokemonName,
                onValueChange = {
                    pokemonName = it
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(8.dp),
                label = {
                    Text(
                        "Nombre pokemon",
                        color = Color.White,
                        fontSize = 15.sp,
                    )
                },
                placeholder = {
                    Text(
                        text = "$pokemonName",
                        color = Color.White,
                        fontSize = 40.sp,
                    )
                },

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
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    textColor = MaterialTheme.colorScheme.tertiary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pokemonID,
                onValueChange = {
                    pokemonID = it
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(8.dp),
                label = {
                    Text(
                        "ID pokemon",
                        color = Color.White,
                        fontSize = 15.sp,
                    )
                },
                placeholder = {
                    Text(
                        text = "$pokemonID",
                        color = Color.White,
                        fontSize = 40.sp,
                    )
                },
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
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    textColor = MaterialTheme.colorScheme.tertiary
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
                    },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                ) {
                Text(
                    text = "Lista otras formas",
                    style = TextStyle(
                        fontSize = 23.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}