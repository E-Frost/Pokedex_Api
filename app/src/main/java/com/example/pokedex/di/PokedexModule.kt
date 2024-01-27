package com.example.pokedex.di

import android.app.Application
import com.example.pokedex.data.repositories.RepositorioApi
import com.example.pokedex.data.repositories.RepositorioLocal
import com.example.pokedex.data.sources.remote.api.PokemonService
import com.example.pokedex.domain.repositories.PokemonRepositoryApi
import com.example.pokedex.domain.repositories.PokemonRepositoryLocal
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokedexModule {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun providePokemonService(): PokemonService {
        return Retrofit.Builder()
            .baseUrl(PokemonService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
    }

    @Singleton
    @Provides
    fun provideGson():Gson{
        return Gson()
    }

    @Provides
    @Singleton
    fun providePokemonRepositoryApi(application: Application, pokemonService: PokemonService): PokemonRepositoryApi {
        return RepositorioApi(application, pokemonService)
    }

    @Provides
    @Singleton
    fun providePokemonRepositoryLocal(application: Application): PokemonRepositoryLocal{
        return RepositorioLocal(application)
    }
}
