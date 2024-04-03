package com.example.pokeapi_koin.details

import com.example.pokeapi_koin.common.APIService
import retrofit2.Response

interface PokemonDetailsRepositoryInterface {
    suspend fun getPokemonDetails(name: String): Response<PokemonDetailsModel>
}

class PokemonDetailsRepository(
    private val apiService: APIService
): PokemonDetailsRepositoryInterface {
    override suspend fun getPokemonDetails(name: String): Response<PokemonDetailsModel> {
        return apiService.getPokemonDetails(name)
    }
}