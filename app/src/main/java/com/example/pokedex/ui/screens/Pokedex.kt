package com.example.pokedex.ui.screens

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.palette.graphics.Palette
import com.example.pokedex.R
import com.example.pokedex.View.TopAppBar
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.ui.components.PokemonHeightAndWeightRow
import com.example.pokedex.ui.components.PokemonCard
import com.example.pokedex.ui.components.PokemonMegaForm
import com.example.pokedex.ui.components.PokemonRegionalForms
import com.example.pokedex.ui.components.PokemonStatsRow
import com.example.pokedex.ui.components.PokemonTypeRow
import com.example.pokedex.ui.viewmodels.PokedexViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun Pokedex(navController: NavHostController, ViewModel: PokedexViewModel) {

    val pokemonState by ViewModel.pokemon.observeAsState()
    var pokemon by remember { mutableStateOf(pokemonState) }
    var checkCount by remember { mutableStateOf(0) }
    var loadingMessage by remember { mutableStateOf("Loading") }


    LaunchedEffect(checkCount) {
        while (true) {

            delay(1000)

            checkCount++
            Log.d("PokedexViewModel", "Contenido: $pokemon")
            Log.d("PokedexViewModel", "Contador $checkCount")
            pokemon = ViewModel.pokemon.value

            loadingMessage = when (loadingMessage) {
                "Loading" -> "Loading."
                "Loading." -> "Loading.."
                "Loading.." -> "Loading..."
                else -> "Loading"
            }
        }
    }

    if (pokemonState != null) {
        PokedexContent(pokemonState!!, navController, ViewModel)

    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = loadingMessage, style = TextStyle(
                    fontSize = 20.sp, color = Color.White
                )
            )
        }
    }
}

@Composable
fun PokedexContent(pokemon: Pokemon?, navController: NavHostController, ViewModel: PokedexViewModel) {

    val context = LocalContext.current

    val bitmap = remember {
        if (pokemon != null) {
            BitmapFactory.decodeResource(context.resources, R.drawable.pokedex_icon)
        } else {
            BitmapFactory.decodeResource(context.resources, R.drawable.pokedex_icon)
        }
    }

    val palette = remember {
        Palette.from(bitmap).generate()
    }

    val systemUiController = rememberSystemUiController()

    val colorCard = palette.dominantSwatch

    val topAppBarColor = colorCard?.let { Color(it.rgb) } ?: MaterialTheme.colorScheme.primary
    val topAppTextColor =
        colorCard?.let { Color(it.titleTextColor) } ?: MaterialTheme.colorScheme.primary
    val statusBarColor = colorCard?.let { Color(it.rgb) } ?: MaterialTheme.colorScheme.primary
    val statusDefault = MaterialTheme.colorScheme.primary

    DisposableEffect(statusBarColor) {
        systemUiController.setStatusBarColor(statusBarColor)
        onDispose {
            systemUiController.setStatusBarColor(statusDefault)
        }
    }

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
    ) {

        item {
            TopAppBar(topAppBarColor, topAppTextColor, pokemon!!.id, navController)
            PokemonCard(pokemon)
            PokemonTypeRow(pokemon)
            PokemonStatsRow(pokemon)
            PokemonHeightAndWeightRow(pokemon)
            PokemonMegaForm(pokemon, ViewModel, navController)
            PokemonRegionalForms(pokemon, ViewModel, navController)
        }
    }
}
