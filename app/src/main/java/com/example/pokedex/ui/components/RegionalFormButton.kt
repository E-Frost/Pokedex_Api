package com.example.pokedex.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedex.ui.viewmodels.PokedexViewModel
import kotlinx.coroutines.launch

@Composable
fun RegionalFormButton(buttonText: String, formName: String, pokemonName: String, ViewModel: PokedexViewModel, navController: NavHostController) {
    val viewModelScope = rememberCoroutineScope()

    val backgroundColor = when (formName.lowercase()) {
        "alola" -> Color(0xFFD2691E)
        "hisui" -> Color(0xFF001F3F)
        "galar" -> Color(0xFF800080)
        else -> MaterialTheme.colorScheme.secondary
    }

    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(color = backgroundColor)
            .padding(5.dp)
            .graphicsLayer(clip = true)
            .clickable {
                viewModelScope.launch {
                    try {
                        val regionalFormPokemonName = "$pokemonName-$formName"
                        val call =
                            ViewModel.pokemonService.getPokemonByName(
                                regionalFormPokemonName
                            )

                        if (call.isSuccessful) {
                            val regionalFormPokemon = call.body()

                            if (regionalFormPokemon != null) {
                                ViewModel.cargarPokemon(regionalFormPokemon.name)
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
            }
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .wrapContentSize(align = Alignment.Center),
            fontSize = 28.sp,
        )
    }
}