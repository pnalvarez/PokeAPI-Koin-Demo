package com.example.pokeapi_koin.common

import com.example.pokeapi_koin.details.PokemonDetailsRepository
import com.example.pokeapi_koin.details.PokemonDetailsRepositoryInterface
import com.example.pokeapi_koin.details.PokemonDetailsViewModel
import com.example.pokeapi_koin.list.PokemonListDataSourceFactory
import com.example.pokeapi_koin.list.PokemonListDataSourceFactoryInterface
import com.example.pokeapi_koin.list.PokemonListPagingSource
import com.example.pokeapi_koin.list.PokemonListRepository
import com.example.pokeapi_koin.list.PokemonListRepositoryInterface
import com.example.pokeapi_koin.list.PokemonListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<APIService> { Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/") // Specify your base URL
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())// Add converter factory for Gson
        .build()
        .create(APIService::class.java)
    }

    single<PokemonListRepositoryInterface> { PokemonListRepository(get()) }
    single<PokemonListPagingSource> { PokemonListPagingSource(get()) }
    single<PokemonListDataSourceFactoryInterface> { PokemonListDataSourceFactory(get()) }
    viewModel { PokemonListViewModel(get()) }

    single<PokemonDetailsRepositoryInterface> { PokemonDetailsRepository(get()) }
    viewModel { PokemonDetailsViewModel(get()) }
}