package com.example.pokeapi_koin.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapi_koin.common.Favorite
import com.example.pokeapi_koin.common.FavoritesRepository
import com.example.pokeapi_koin.common.FavoritesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val repository: PokemonDetailsRepositoryInterface,
    private val favoritesRepository: FavoritesRepositoryInterface
): ViewModel() {
    private val _pokemonDetails = MutableStateFlow<PokemonDetailsModel?>(null)
    private val _isLoading = MutableStateFlow<Boolean>(true)
    private val _gotError = MutableStateFlow<Boolean>(false)
    private val _isFavorite = MutableStateFlow<Boolean>(false)
    private var favoriteModel: Favorite? = null
    val pokemonDetails: StateFlow<PokemonDetailsModel?> get() = _pokemonDetails.asStateFlow()
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()
    val gotError: StateFlow<Boolean> get() = _gotError.asStateFlow()
    val isFavorite: StateFlow<Boolean> get() = _isFavorite.asStateFlow()

    fun fetchDetails(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            getIsFavorite(name)
            val result = repository.getPokemonDetails(name)
            val error = result.errorBody()
            val data = result.body()
            if (error != null || !result.isSuccessful) {
                Log.d("Got an error", "Got an error")
                _isLoading.value = false
                _gotError.value = true
                return@launch
            }
            if (data != null) {
                Log.d("Got data", "Got data")
                _isLoading.value = false
                _pokemonDetails.value = data
            } else {
                Log.d("Got nothing", "Got data")
                _isLoading.value = false
            }
        }
    }

    fun didClickFavorite() {
        viewModelScope.launch {
            if (_isFavorite.value) {
                favoriteModel?.let {
                    favoritesRepository.deleteFavorite(it)
                }
            } else {
                pokemonDetails.value?.let {
                    favoritesRepository.insertFavorite(Favorite("${it.id}", it.name))
                }
            }
        }
        _isFavorite.value = !_isFavorite.value
    }

    private suspend fun getIsFavorite(name: String) {
        favoriteModel = favoritesRepository.getFavoriteByName(name)
        _isFavorite.value = favoriteModel != null
    }
}