package com.example.pokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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