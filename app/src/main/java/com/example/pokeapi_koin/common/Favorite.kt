package com.example.pokeapi_koin.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("name")
    val name: String
)