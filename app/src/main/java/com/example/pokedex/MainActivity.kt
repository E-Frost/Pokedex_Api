package com.example.pokedex

import android.annotation.SuppressLint
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
import com.example.pokedex.view.Pokedex
import com.example.pokedex.viewmodel.PokedexViewModel
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.view.OtherFormsListScreen
import com.example.pokedex.view.PokedexInitialScreen
import com.example.pokedex.view.PokemonListScreen
import com.example.pokedex.view.SearchScreen

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
                            PokedexInitialScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("Buscador"){
                            SearchScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("pokedex") {
                            Pokedex(viewModel, navController)
                        }
                        composable("listaApi"){
                            PokemonListScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("listaApiOtrasFormas"){
                            OtherFormsListScreen(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}



