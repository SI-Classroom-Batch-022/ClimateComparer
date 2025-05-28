package com.example.climatecomparer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteCitys")
data class FavoriteCity (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)