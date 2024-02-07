package com.example.pokedex.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

@Composable
fun PokemonRegionalForms(pokemon: Pokemon, ViewModel: PokedexViewModel, navController: NavHostController) {
    val pokemonName = pokemon.name
    val otherFormsPokemonList by ViewModel.pokemonListOtherForms.observeAsState(emptyList())
    val galar = "galar"
    val alola = "alola"
    val hisui = "hisui"

    val alolaForm = remember(pokemonName, otherFormsPokemonList) {
        checkForm(pokemonName, otherFormsPokemonList, alola)
    }

    val galarForm = remember(pokemonName, otherFormsPokemonList) {
        checkForm(pokemonName, otherFormsPokemonList, galar)
    }

    val hisuiForm = remember(pokemonName, otherFormsPokemonList) {
        checkForm(pokemonName, otherFormsPokemonList, hisui)
    }


    if ((alolaForm && !pokemonName.lowercase().contains("-$alola")) ||
        (galarForm && !pokemonName.lowercase().contains("-$galar")) ||
        (hisuiForm && !pokemonName.lowercase().contains("-$hisui"))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (alolaForm) {
                RegionalFormButton("Alola", alola, pokemonName, ViewModel, navController)
            }

            if (galarForm) {
                RegionalFormButton("Galar", galar, pokemonName, ViewModel, navController)
            }

            if (hisuiForm) {
                RegionalFormButton("Hisui", hisui, pokemonName, ViewModel, navController)
            }
        }
    }
}
fun checkForm(pokemonName: String?, pokemonList: List<PokemonListItem>, formName: String): Boolean {
    val formattedPokemonName = pokemonName?.lowercase()

    val isFormPresent = pokemonList.any {
        it.name.lowercase()
            .startsWith(formattedPokemonName ?: "") && formName in it.name.lowercase()
    }

    if (isFormPresent) {
        println("$formattedPokemonName-$formName está presente en la lista.")
        return true
    } else {
        println("$formattedPokemonName-$formName no está presente en la lista principal.")
    }
    return false
}