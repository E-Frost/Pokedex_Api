package com.example.pokedex.data.maps

import com.example.pokedex.domain.models.OficialArtwork
import com.example.pokedex.domain.models.Other
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.Sprites
import com.example.pokedex.domain.models.StatData
import com.example.pokedex.domain.models.TypeData
import com.example.pokedex.domain.models.TypeInfo
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class PokemonMapper : JsonDeserializer<Pokemon> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Pokemon? {
        val id = json?.asJsonObject?.get("id")?.asInt
        val name = json?.asJsonObject?.get("name")?.asString.orEmpty()
            .replaceFirstChar { it.uppercaseChar() }
        val height = json?.asJsonObject?.get("height")?.asFloat?.div(10)
        val weight = json?.asJsonObject?.get("weight")?.asFloat?.div(10)
        val types = getPokemonTypeList(json?.asJsonObject)
        val stats = getPokemonStatList(json?.asJsonObject)
        val frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
        val frontShiny = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/${id}.png"

        return id?.let {
            Pokemon(
                id = it,
                name = name,
                sprites = Sprites(
                    other = Other(
                        officialArtwork = OficialArtwork(
                            frontDefault = frontDefault,
                            frontShiny = frontShiny
                        )
                    )
                ),
                types = types,
                weight = weight,
                height = height,
                stats = stats
            )
        }
    }

    private fun getPokemonTypeList(pokemonJSONObject: JsonObject?): List<TypeData> {
        val typeList = mutableListOf<TypeData>()
        val types = pokemonJSONObject?.get("types")?.asJsonArray
        types?.forEach {
            val type = it.asJsonObject
            val typeName = type.get("type").asJsonObject.get("name").asString.lowercase()
            val slot = type.get("slot").asInt
            typeList.add(TypeData(slot = slot, type = TypeInfo(name = typeName, url = "")))
        }
        return typeList
    }

    private fun getPokemonStatList(pokemonJSONObject: JsonObject?): List<StatData> {
        val statList = mutableListOf<StatData>()
        val statsArray = pokemonJSONObject?.getAsJsonArray("stats")

        statsArray?.forEach { statElement ->
            val statObject = statElement.asJsonObject
            val statName = statObject.getAsJsonObject("stat")?.get("name")?.asString
            val baseStat = statObject.get("base_stat")?.asInt

            statName?.let { name ->
                baseStat?.let { value ->
                    statList.add(StatData(base_stat = value, effort = 0, stat = TypeInfo(name = name, url = "")))
                }
            }
        }
        return statList
    }
}