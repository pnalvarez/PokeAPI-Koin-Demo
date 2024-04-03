package com.example.pokeapi_koin.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface PokemonListDataSourceFactoryInterface {
    fun getPokemon(): Flow<PagingData<PokemonListItem>>
}

class PokemonListDataSourceFactory(
    private val pagingSource: PokemonListPagingSource
): PokemonListDataSourceFactoryInterface {
    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }

    override fun getPokemon() = Pager(
        // Metadata about pagination
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        // Paging Source instance
        pagingSourceFactory = { pagingSource }
    ).flow
// Returns a pagination flow to be listened by the UI(like an observable)
}