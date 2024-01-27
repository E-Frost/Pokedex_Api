package com.example.pokedex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import com.example.pokedex.domain.models.Pokemon

@Composable
fun PokemonHeightAndWeightRow(pokemon: Pokemon) {
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
                text = " " + (pokemon.weight?.div(10)).toString() + " KG",
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
                text = " " + (pokemon.height?.div(10)).toString() + " M ",
                color = Color.White,
                fontSize = 22.sp
            )
        }

        Column(
            modifier = Modifier.weight(0.1f)
        ) {}
    }
}