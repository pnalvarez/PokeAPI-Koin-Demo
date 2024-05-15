package com.example.pokeapi_koin.favoritelist

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pokeapi_koin.common.Favorite
import com.example.pokeapi_koin.common.FavoritesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoriteListViewModel(
    private val repository: FavoritesRepositoryInterface
): ViewModel() {
    private val _favoriteList = MutableStateFlow<List<Favorite>>(listOf())

    val favoriteList: StateFlow<List<Favorite>> get() = _favoriteList
    init {
        getFavoritePokemon()
    }

    private fun getFavoritePokemon() {
        viewModelScope.launch {
            repository
                .getFavorites()
                .distinctUntilChanged()
                .map { it.sortedBy { it.id } }
                .collect { _favoriteList.value = it }
        }
    }

    fun didClickDelete(favorite: Favorite) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
    }
}