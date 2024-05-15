package com.example.pokeapi_koin.common

import com.example.pokeapi_koin.details.PokemonDetailsModel
import com.example.pokeapi_koin.list.PokemonListModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Logger

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TrackEvent(
    val event: String,
    val attributes: Array<String>
)

interface APIService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonListModel>

    @Logger
    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): Response<PokemonDetailsModel>
}

class EventTracker {
    fun trackEvent(event: String, attributes: Array<String>) {
        // Analytics implementation
    }
}