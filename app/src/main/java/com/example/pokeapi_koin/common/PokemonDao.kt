package com.example.pokeapi_koin.common

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorites WHERE name = :name")
    suspend fun getFavoriteByName(name: String): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}