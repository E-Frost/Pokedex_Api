package com.example.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class Estadisticas(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    @SerializedName("special-attack") val specialAttack: Int,
    @SerializedName("special-defense") val specialDefense: Int,
    val speed: Int
)
