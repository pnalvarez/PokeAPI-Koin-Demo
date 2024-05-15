package com.example.pokeapi_koin.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.androidx.compose.get

class ViewModelFactory(private val dataSourceFactory: PokemonListDataSourceFactoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
            return PokemonListViewModel(dataSourceFactory = dataSourceFactory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}