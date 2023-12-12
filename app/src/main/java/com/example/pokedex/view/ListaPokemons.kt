package com.example.pokedex.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedex.viewmodel.PokedexViewModel
import java.util.Locale

@Composable
fun ListaPokemons(navController: NavHostController, viewModel: PokedexViewModel) {
    val nombresPokemon = mapOf(
        "bulbasaur" to 1,
        "ivysaur" to 2,
        "venusaur" to 3,
        "charmander" to 4,
        "charmeleon" to 5,
        "charizard" to 6,
        "squirtle" to 7,
        "wartortle" to 8,
        "blastoise" to 9,
        "pikachu" to 25,
        "ditto" to 132,
        "tyranitar" to 248,
        "aggron" to 306,
        "metagross" to 376,
        "haxorus" to 612,
        "hydreigon" to 635,
        "grimmsnarl" to 861
    )

    //val pokemonState = viewModel.pokemon.observeAsState()

    LazyColumn (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
        ) {

        items(nombresPokemon.keys.toList()) { nombrePokemon ->
            val id = nombresPokemon[nombrePokemon]

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .clickable {
                        viewModel.cargarPokemon(nombrePokemon)
                        navController.navigate("pokedex")
                    },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "$id# ${nombrePokemon.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                        }}",
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