package com.example.pokedex

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.ui.screens.Pokedex
import com.example.pokedex.ui.viewmodels.PokedexViewModel
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.ui.screens.OtherFormsListScreen
import com.example.pokedex.ui.screens.PokedexInitialScreen
import com.example.pokedex.ui.screens.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Pokedex : Application() {
}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                val navController = rememberNavController()

                val viewModel: PokedexViewModel by viewModels()

                Scaffold {
                    Column {

                    }
                    NavHost(
                        navController = navController,
                        startDestination = "pantallaInicial"
                    ) {
                        composable("pantallaInicial"){
                            PokedexInitialScreen(navController,viewModel)
                        }
                        composable("pokedex") {
                            Pokedex(navController, viewModel)
                        }
                        composable("listaApi"){
                            PokemonListScreen(navController, viewModel)
                        }
                        composable("listaApiOtrasFormas"){
                            OtherFormsListScreen(navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}



