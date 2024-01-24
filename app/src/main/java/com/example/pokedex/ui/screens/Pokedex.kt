package com.example.pokedex.ui.screens

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.View.TopAppBar
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonListItem
import com.example.pokedex.domain.models.Tipo
import com.example.pokedex.domain.models.obtenerTipoConColores
import com.example.pokedex.ui.theme.ataqueColors
import com.example.pokedex.ui.theme.ataqueEspecialColors
import com.example.pokedex.ui.theme.defensaColors
import com.example.pokedex.ui.theme.defensaEspecialColors
import com.example.pokedex.ui.theme.saludColors
import com.example.pokedex.ui.theme.velocidadColors
import com.example.pokedex.ui.viewmodels.PokedexViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun Pokedex(navController: NavHostController, ViewModel: PokedexViewModel) {

    val pokemonState by ViewModel.pokemon.observeAsState()
    var pokemon by remember { mutableStateOf(pokemonState) }
    var checkCount by remember { mutableStateOf(0) }
    var loadingMessage by remember { mutableStateOf("Loading") }

    val pokemonName = pokemon?.name
    var megaForm: Boolean by remember { mutableStateOf(false) }
    val pokemonList by ViewModel.pokemonListOtherForms.observeAsState(emptyList())
    if ( checkMegaForm(pokemonName, megaForm, pokemonList)){
        megaForm = true
    }


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
        PokedexContent(pokemonState!!, navController, megaForm)

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

fun checkMegaForm(pokemonName: String?, megaForm: Boolean, pokemonList: List<PokemonListItem>): Boolean {

    if (megaForm) {
        val megaFormName = "$pokemonName-mega"
        return pokemonList.any { it.name.equals(megaFormName, ignoreCase = true) }
    }
    return false
}

@Composable
fun PokedexContent(pokemon: Pokemon?, navController: NavHostController, megaForm: Boolean) {

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
            if (megaForm == true) {
                PokemonMegaForm(pokemon)
            }
            PokemonTypeRow(pokemon)
            PokemonStatsRow(pokemon)
            HeightAndWeightPokemonRow(pokemon)
        }
    }
}


@Composable
fun PokemonCard(pokemon: Pokemon) {

    val primaryTypeColor =
        pokemon.types.firstOrNull()?.let { obtenerTipoConColores(it).colores.firstOrNull() }
    var shiny by remember { mutableStateOf(false) }

    Column {

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .clickable {
                    shiny = !shiny
                },
            colors = CardDefaults.cardColors(
                containerColor = primaryTypeColor
                    ?: MaterialTheme.colorScheme.secondary
            ),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                AsyncImage(
                    model =
                    if (!shiny) {
                        pokemon.sprites.other.officialArtwork.frontDefault
                    } else {
                        pokemon.sprites.other.officialArtwork.frontShiny
                    },
                    contentDescription = "Pokemon image",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .size(200.dp),
                    onSuccess = { success ->
                        val drawable = success.result.drawable
                    }
                )

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    painterResource(id = R.drawable.diamond),
                    contentDescription = "Shiny",
                    tint = if (shiny) primaryTypeColor
                        ?: MaterialTheme.colorScheme.secondary else Color.Transparent,
                    modifier = Modifier.size(40.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(5f)
            ) {
                Text(
                    text = pokemon.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    },
                    style = TextStyle(
                        fontSize = 40.sp, color = Color.White, textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
            }
        }
    }
}


@Composable
fun PokemonTypeRow(pokemon: Pokemon) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        pokemon.types.forEach { typeData ->
            PokemonTypeButton(
                tipo = obtenerTipoConColores(typeData), modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PokemonMegaForm(pokemon: Pokemon, modifier: Modifier = Modifier) {

    Box(
        modifier
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(8.dp)
            .graphicsLayer(clip = true)
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

@Composable
fun PokemonTypeButton(tipo: Tipo, modifier: Modifier = Modifier) {
    Box(
        modifier
            .clip(MaterialTheme.shapes.medium)
            .background(
                brush = Brush.linearGradient(tipo.colores), shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp)
            .graphicsLayer(clip = true)
    ) {
        Text(
            text = tipo.nombre.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .wrapContentSize(align = Alignment.Center),
            fontSize = 28.sp,
        )
    }
}

@Composable
fun PokemonStatsRow(pokemon: Pokemon) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val estadisticasTotales = pokemon.stats.sumBy { it.base_stat }

        for (statData in pokemon.stats) {
            StatsRow(
                getStatName(statData.stat.name),
                statData.base_stat,
                getStatColors(statData.stat.name)
            )
        }

        Row {
            Text(
                text = "Total stats: ", style = TextStyle(
                    fontSize = 25.sp, color = Color.White, textAlign = TextAlign.Right
                ), modifier = Modifier.weight(0.5f)
            )

            Text(
                text = estadisticasTotales.toString(), style = TextStyle(
                    fontSize = 25.sp, color = Color.White, textAlign = TextAlign.Center
                ), modifier = Modifier.weight(0.5f)
            )
        }
    }
}

private fun getStatColors(statName: String): List<Color> {
    return when (statName) {
        "hp" -> saludColors
        "attack" -> ataqueColors
        "special-attack" -> ataqueEspecialColors
        "defense" -> defensaColors
        "special-defense" -> defensaEspecialColors
        "speed" -> velocidadColors
        else -> emptyList()
    }
}

private fun getStatName(statName: String): String {
    return when (statName) {
        "hp" -> "HP"
        "attack" -> "ATK"
        "special-attack" -> "S ATK"
        "defense" -> "Defense"
        "special-defense" -> "S DEF"
        "speed" -> "SPEED"
        else -> statName
    }
}

@Composable
fun StatsRow(name: String, estadistica: Int, gradientColors: List<Color>) {
    val valormaximo = 255
    val valorBarra = (((estadistica + 25) / valormaximo.toFloat())).coerceIn(0f, 1f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name, style = TextStyle(
                fontSize = 20.sp, color = Color.White, textAlign = TextAlign.Right
            ), modifier = Modifier.weight(0.5f)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        (Color.Gray), shape = MaterialTheme.shapes.medium
                    )
                    .graphicsLayer(clip = true)
                    .fillMaxSize()
            ) {
                Text(
                    text = estadistica.toString(),
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentSize(align = Alignment.Center),
                    fontSize = 15.sp,
                )
            }

            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        brush = Brush.horizontalGradient(gradientColors),
                        shape = MaterialTheme.shapes.medium
                    )
                    .graphicsLayer(clip = true)
                    .fillMaxWidth(valorBarra)
            ) {
                Text(
                    text = estadistica.toString(),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentSize(align = Alignment.Center),
                    fontSize = 15.sp,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.05f)
        ) {

        }
    }
}

@Composable
fun HeightAndWeightPokemonRow(pokemon: Pokemon) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(0.1f)
        ) {}

        Row(
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painterResource(id = R.drawable.weight),
                contentDescription = "peso",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = " " + (pokemon.weight / 10).toString() + " KG",
                color = Color.White,
                fontSize = 22.sp
            )
        }

        Column(
            modifier = Modifier.weight(0.05f)
        ) {}

        Row(
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painterResource(id = R.drawable.height),
                contentDescription = "altura",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = " " + (pokemon.height / 10).toString() + " M ",
                color = Color.White,
                fontSize = 22.sp
            )
        }

        Column(
            modifier = Modifier.weight(0.1f)
        ) {}
    }
}