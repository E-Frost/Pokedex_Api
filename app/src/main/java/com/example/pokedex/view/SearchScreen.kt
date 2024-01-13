package com.example.pokedex.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.repository.api.PokemonService
import com.example.pokedex.viewmodel.PokedexViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun SearchScreen(navController: NavHostController, viewModel: PokedexViewModel) {
    var error by remember { mutableStateOf<String?>(null) }
    var searchText by remember { mutableStateOf(TextFieldValue()) }

    val viewModelScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        BasicTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.White
            )
        )

        Button(
            onClick = {
                viewModelScope.launch {
                    try {
                        val call = viewModel.pokemonService.getPokemonByName(searchText.text)

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
                                error = "Error en la respuesta de la API: Pokemon nulo"
                            }
                        } else {
                            Log.e(
                                "PokedexApp",
                                "Error en la respuesta de la API: ${call.message()}"
                            )
                            error = "Error en la respuesta de la API: ${call.message()}"
                        }
                    } catch (e: Exception) {
                        Log.e("PokedexApp", "Error al realizar la solicitud a la API: ${e.message}")
                        error = "Error al realizar la solicitud a la API: ${e.message}"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Realizar Solicitud a la API")
        }
        error?.let {
            Text(text = "Error:\n$it", color = Color.Red)
        }
    }
}