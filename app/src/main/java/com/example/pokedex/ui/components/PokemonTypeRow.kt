package com.example.pokedex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.obtenerTipoConColores

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