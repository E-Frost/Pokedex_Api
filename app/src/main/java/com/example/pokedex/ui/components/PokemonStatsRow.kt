package com.example.pokedex.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.ui.theme.ataqueColors
import com.example.pokedex.ui.theme.ataqueEspecialColors
import com.example.pokedex.ui.theme.defensaColors
import com.example.pokedex.ui.theme.defensaEspecialColors
import com.example.pokedex.ui.theme.saludColors
import com.example.pokedex.ui.theme.velocidadColors

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
fun getStatColors(statName: String): List<Color> {
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

fun getStatName(statName: String): String {
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