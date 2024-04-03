package com.example.pokeapi_koin.list

import android.util.Log
import com.example.pokeapi_koin.common.APIService
import org.koin.androidx.compose.get
import retrofit2.Response

interface PokemonListRepositoryInterface {
    suspend fun getPokemonList(offset: Int, limit: Int): Response<PokemonListModel>
}

class PokemonListRepository(
    private val apiService: APIService
): PokemonListRepositoryInterface {
    override suspend fun getPokemonList(offset: Int, limit: Int): Response<PokemonListModel> {
        Log.d("Repository getPokemonList", "$offset, $limit")
        return apiService.getPokemonList(offset = offset, limit = limit)
    }
}