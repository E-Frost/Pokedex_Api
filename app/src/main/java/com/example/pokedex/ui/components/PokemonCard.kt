package com.example.pokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.obtenerTipoConColores
import java.util.Locale

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
                    painterResource(id = R.drawable.shiny),
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
                        fontSize = 35.sp, color = Color.White, textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 1
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

