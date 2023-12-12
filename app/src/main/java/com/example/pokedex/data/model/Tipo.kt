package com.example.pokedex.data.model

import androidx.compose.ui.graphics.Color
import com.example.pokedex.ui.theme.bugColors
import com.example.pokedex.ui.theme.darkColors
import com.example.pokedex.ui.theme.dragonColors
import com.example.pokedex.ui.theme.electricColors
import com.example.pokedex.ui.theme.fairyColors
import com.example.pokedex.ui.theme.fightingColors
import com.example.pokedex.ui.theme.fireColors
import com.example.pokedex.ui.theme.flyingColors
import com.example.pokedex.ui.theme.ghostColors
import com.example.pokedex.ui.theme.grassColors
import com.example.pokedex.ui.theme.groundColors
import com.example.pokedex.ui.theme.iceColors
import com.example.pokedex.ui.theme.normalColors
import com.example.pokedex.ui.theme.poisonColors
import com.example.pokedex.ui.theme.psychicColors
import com.example.pokedex.ui.theme.rockColors
import com.example.pokedex.ui.theme.steelColors
import com.example.pokedex.ui.theme.waterColors

data class Tipo(val nombre: String, val colores: List<Color>) {

}

val tipoColores: Map<String, List<Color>> = mapOf(
    "normal" to normalColors,
    "fire" to fireColors,
    "water" to waterColors,
    "grass" to grassColors,
    "electric" to electricColors,
    "ice" to iceColors,
    "fighting" to fightingColors,
    "poison" to poisonColors,
    "ground" to groundColors,
    "flying" to flyingColors,
    "psychic" to psychicColors,
    "bug" to bugColors,
    "rock" to rockColors,
    "ghost" to ghostColors,
    "dark" to darkColors,
    "steel" to steelColors,
    "fairy" to fairyColors,
    "dragon" to dragonColors
)

fun obtenerTipoConColores(typeData: TypeData): Tipo {
    val tipoNombre = typeData.type.name.lowercase()
    val colores = tipoColores[tipoNombre] ?: emptyList()
    return Tipo(tipoNombre, colores)
}
