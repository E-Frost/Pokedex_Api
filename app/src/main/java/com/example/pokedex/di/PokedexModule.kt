package com.example.pokedex.di

import android.app.Application
import com.example.pokedex.data.repositories.RepositorioApiImpl
import com.example.pokedex.data.repositories.RepositorioLocalImpl
import com.example.pokedex.data.sources.remote.api.PokemonService
import com.example.pokedex.domain.repositories.IPokemonRepositoryApi
import com.example.pokedex.domain.repositories.IPokemonRepositoryLocal
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun providePokemonRepositoryApi(application: Application, pokemonService: PokemonService): IPokemonRepositoryApi {
        return RepositorioApiImpl(application, pokemonService)
    }

    @Provides
    @Singleton
    fun providePokemonRepositoryLocal(application: Application): IPokemonRepositoryLocal{
        return RepositorioLocalImpl(application)
    }
}
