package com.example.pokedex.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedex.data.model.PokemonListItem
import com.example.pokedex.data.repository.PokemonService
import com.example.pokedex.viewmodel.PokedexViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

@Composable
fun PokemonListScreen(navController: NavHostController, viewModel: PokedexViewModel) {
    val pokemonListLimit = 20
    var pokemonList by remember { mutableStateOf<List<PokemonListItem>>(emptyList()) }
    var pokemonIds by remember { mutableStateOf<List<Int>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        val retrofit = Retrofit.Builder()
            .baseUrl(PokemonService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pokemonService = retrofit.create(PokemonService::class.java)

        try {
            val response = pokemonService.getAllPokemon(limit = pokemonListLimit)
            if (response.isSuccessful) {
                pokemonList = response.body()?.results ?: emptyList()
                pokemonIds = (1..pokemonList.size).toList()
                error = null
            } else {
                error = "Error en la respuesta de la API: ${response.message()}"
            }
        } catch (e: Exception) {
            error = "Error al realizar la solicitud a la API: ${e.message}"
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
                        //viewModel.cargarPokemon(nombrePokemon)
                        // navController.navigate("pokedex")
                    },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp)
                ) {
                    if (currentPokemonId != null) {
                        Text(
                            text = "#$currentPokemonId " + " - "  +" ${
                                pokemon.name.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                }
                            }",
                            style = TextStyle(
                                fontSize = 30.sp, color = Color.White, textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}