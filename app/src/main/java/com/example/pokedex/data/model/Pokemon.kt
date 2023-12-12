package com.example.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    var id: Int,
    var name: String,
    var sprites: Sprites,
    var types: List<TypeData>,
    var weight: Double,
    var height: Double,
    var stats: List<StatData>
)

data class Sprites(
    @SerializedName("other")
    val other: Other
)

data class Other(
    @SerializedName("official-artwork")
    val officialArtwork: OficialArtwork
)

data class OficialArtwork(
    @SerializedName("front_default")
    val frontDefault : String,
    @SerializedName("front_shiny")
    val frontShiny :String
)

data class TypeData(
    val slot: Int,
    val type: TypeInfo
)

data class TypeInfo(
    val name: String,
    val url: String
)

data class StatData(
    val base_stat: Int,
    val effort: Int,
    val stat: TypeInfo
)
