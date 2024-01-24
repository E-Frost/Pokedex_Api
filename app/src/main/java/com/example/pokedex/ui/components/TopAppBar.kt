package com.example.pokedex.View

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedex.ui.theme.tipografiaPokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    colorBarra: Color,
    colorTexto: Color,
    idPokemon: Int,
    navController: NavHostController
) {
    val topAppBarColor = colorBarra
    val topAppBarTextColor = colorTexto
    val idetificadorPokemon = idPokemon

    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Atras",
                    tint = topAppBarTextColor,
                )
            }

        },
        title = {
            Row {

                Text(
                    text = "Pokedex",
                    style = TextStyle(
                        fontFamily = tipografiaPokemon,
                        color = topAppBarTextColor,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.width(170.dp))

                Text(
                    text = "#" + idetificadorPokemon,
                    style = TextStyle(
                        fontFamily = tipografiaPokemon,
                        color = topAppBarTextColor,
                        fontSize = 25.sp,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(topAppBarColor),
    )
}