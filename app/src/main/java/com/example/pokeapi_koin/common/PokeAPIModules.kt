package com.example.pokeapi_koin.common

import android.content.Context
import android.media.metrics.Event
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.pokeapi_koin.details.PokemonDetailsRepository
import com.example.pokeapi_koin.details.PokemonDetailsRepositoryInterface
import com.example.pokeapi_koin.details.PokemonDetailsViewModel
import com.example.pokeapi_koin.list.PokemonListDataSourceFactory
import com.example.pokeapi_koin.list.PokemonListDataSourceFactoryInterface
import com.example.pokeapi_koin.list.PokemonListPagingSource
import com.example.pokeapi_koin.list.PokemonListRepository
import com.example.pokeapi_koin.list.PokemonListRepositoryInterface
import com.example.pokeapi_koin.list.PokemonListViewModel
import com.example.pokeapi_koin.list.ViewModelFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<OkHttpClient> {
        OkHttpClient()
            .newBuilder()
            .apply { addInterceptor(get()) }
            .build()
    }

    single<APIService> { Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/") // Specify your base URL
        .addConverterFactory(GsonConverterFactory.create())
        // Add HTTP Interceptor
        .client(get())// Add converter factory for Gson
        .build()
        .create(APIService::class.java)
    }

    single<PokemonDatabase> {
        providePokemonDatabase(androidContext())
    }

    single<PokemonDao> {
        providePokemonDao(get())
    }

    single { EventTracker() }
    single<Interceptor> { LogInterceptor(get()) }
    single<PokemonListRepositoryInterface> { PokemonListRepository(get()) }
    single<PokemonListPagingSource> { PokemonListPagingSource(get()) }
    factory<PokemonListDataSourceFactoryInterface> { PokemonListDataSourceFactory(get()) }
    viewModel<PokemonListViewModel> { PokemonListViewModel(get()) }
    single { ViewModelFactory(get()) }

    single<PokemonDetailsRepositoryInterface> { PokemonDetailsRepository(get()) }
    single<FavoritesRepositoryInterface> { FavoritesRepository(get()) }
    viewModel { PokemonDetailsViewModel(get(), get()) }
}

fun providePokemonDao(database: PokemonDatabase): PokemonDao {
    return database.pokemonDao()
}


fun providePokemonDatabase(context: Context): PokemonDatabase {
    return Room.databaseBuilder(
        context,
        PokemonDatabase::class.java,
        "weather_database"
    )
        .fallbackToDestructiveMigration()
        .build()
}

