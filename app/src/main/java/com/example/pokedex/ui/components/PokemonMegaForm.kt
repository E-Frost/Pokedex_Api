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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.ui.viewmodels.PokedexViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun PokemonMegaForm(pokemon: Pokemon, ViewModel: PokedexViewModel, navController: NavHostController) {
    val viewModelScope = rememberCoroutineScope()
    val pokemonName = pokemon.name
    val pokemonMegaName = if (pokemonName.equals("charizard", ignoreCase = true)) {
        if (LocalDateTime.now().minute % 2 == 0) {
            "$pokemonName-mega-x"
        } else {
            "$pokemonName-mega-y"
        }
        //Es un cornercase del carjo y no se me ha ocurido otra forma de tratarlo mas simple
    } else {
        "$pokemonName-mega"
    }

    val otherFormsPokemonList by ViewModel.pokemonListOtherForms.observeAsState(emptyList())

    val megaForm = remember(pokemonName, otherFormsPokemonList) {
        checkMegaForm(pokemonName, otherFormsPokemonList)
    }

    if ( megaForm && !pokemonName.lowercase().contains("mega")) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colorScheme.secondary)
                .padding(8.dp)
                .graphicsLayer(clip = true)
                .clickable {
                    viewModelScope.launch {
                        try {
                            val call = ViewModel.pokemonService.getPokemonByName(pokemonMegaName)

                            if (call.isSuccessful) {
                                val pokemonFromApi = call.body()

                                if (pokemonFromApi != null) {
                                    ViewModel.cargarPokemon(pokemonFromApi.name)
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
                text = "Mega",
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentSize(align = Alignment.Center),
                fontSize = 28.sp,
            )
        }
    }
}

fun checkMegaForm(pokemonName: String?, pokemonList: List<PokemonListItem>): Boolean {

    val formattedPokemonName = pokemonName?.lowercase()

    val isMegaFormPresent = pokemonList.any {
        it.name.lowercase().startsWith(formattedPokemonName ?: "") && "mega" in it.name.lowercase()
    }

    if (isMegaFormPresent) {
        println("$formattedPokemonName-mega está presente en la lista .")
        return true
    } else {
        println("$formattedPokemonName-mega no está presente en la lista principal.")
    }
    return false
}