package com.example.pokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.domain.models.Tipo
import java.util.Locale

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