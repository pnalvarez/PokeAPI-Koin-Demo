package com.example.pokeapi_koin.common

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([Favorite::class], version = 1, exportSchema = false)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}