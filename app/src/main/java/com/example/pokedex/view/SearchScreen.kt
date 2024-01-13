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
import com.example.pokedex.data.repository.api.PokemonService
import com.example.pokedex.viewmodel.PokedexViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun SearchScreen(navController: NavHostController, viewModel: PokedexViewModel) {
    var apiResponse by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var searchText by remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current
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
                    val retrofit = Retrofit.Builder()
                        .baseUrl(PokemonService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val pokemonService = retrofit.create(PokemonService::class.java)
                    val call = pokemonService.getPokemonByDexNumOrName(searchText.text)

                    try {
                        val response = call?.execute()
                        if (response != null) {
                            Log.d("PokedexApp", "Response code: ${response.code()}")
                        }

                        if (response != null) {
                            if (response.isSuccessful) {
                                apiResponse = response.body()?.toString()
                                error = null
                            } else {
                                Log.e("PokedexApp", "Error en la respuesta de la API: ${response.message()}")
                                error = "Error en la respuesta de la API: ${response.message()}"
                                apiResponse = null
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("PokedexApp", "Error al realizar la solicitud a la API: ${e.message}")
                        error = "Error al realizar la solicitud a la API: ${e.message}"
                        apiResponse = null
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Realizar Solicitud a la API")
        }

        apiResponse?.let {
            Text(text = "Respuesta de la API:\n$it", color = Color.White)
        }

        error?.let {
            Text(text = "Error:\n$it", color = Color.Red)
        }
    }
}


    /*
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    var pokemon: Pokemon? by remember { mutableStateOf(null) }
    var error: String? by remember { mutableStateOf(null) }

    val context = LocalContext.current
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
                    val retrofit = Retrofit.Builder()
                        .baseUrl(PokemonService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(Gson()))
                        .build()

                    val pokemonService = retrofit.create(PokemonService::class.java)
                    val call = pokemonService.getPokemonByDexNumOrName(searchText.text)

                    try {
                        val response = call?.execute()
                        if (response != null) {
                            if (response.isSuccessful) {

                                pokemon = RepositorioJson(context)
                                    .cargarDatosDesdeJSON(response.body()?.name.orEmpty())
                                    ?: response.body()
                            } else {
                                error = "Error al cargar Pokémon: ${response.message()}"
                            }
                        }
                    } catch (e: Exception) {
                        error = "Error al cargar Pokémon: ${e.message}"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Buscar Pokémon")
        }

        if (pokemon != null) {
            viewModel.cargarPokemon(pokemon!!.name)
            navController.navigate("pokedex")
        }
    }
}

     */