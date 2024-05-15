package com.example.pokeapi_koin.common

import kotlinx.coroutines.flow.Flow

interface FavoritesRepositoryInterface {
    fun getFavorites(): Flow<List<Favorite>>
    suspend fun getFavoriteByName(name: String): Favorite?
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun updateFavorite(favorite: Favorite)
    suspend fun deleteFavorite(favorite: Favorite)
    suspend fun deleteAllFavorites()
}
class FavoritesRepository(
    private val dao: PokemonDao
): FavoritesRepositoryInterface {
    override fun getFavorites(): Flow<List<Favorite>> = dao.getFavorites()
    override suspend fun getFavoriteByName(name: String): Favorite? = dao.getFavoriteByName(name)
    override suspend fun insertFavorite(favorite: Favorite) = dao.insertFavorite(favorite)
    override suspend fun updateFavorite(favorite: Favorite) = dao.updateFavorite(favorite)
    override suspend fun deleteFavorite(favorite: Favorite) = dao.deleteFavorite(favorite)
    override suspend fun deleteAllFavorites() = dao.deleteAllFavorites()
}