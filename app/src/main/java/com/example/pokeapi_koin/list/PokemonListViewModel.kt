package com.example.pokeapi_koin.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val dataSourceFactory: PokemonListDataSourceFactoryInterface
): ViewModel() {
    private val _pagingData: MutableStateFlow<PagingData<PokemonListItem>> = MutableStateFlow(
        PagingData.empty())
    val pagingData: StateFlow<PagingData<PokemonListItem>> get() = _pagingData.asStateFlow()

    init {
        viewModelScope.launch {
            getPokemon()
        }
    }

    private suspend fun getPokemon() {
        dataSourceFactory
            .getPokemon()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _pagingData.value = it
            }
    }
}