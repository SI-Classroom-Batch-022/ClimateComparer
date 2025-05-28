package com.example.climatecomparer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.climatecomparer.data.model.FavoriteCity

@Database(entities = [FavoriteCity::class], version = 1, exportSchema = false)
abstract class FavoriteCitiesDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: FavoriteCitiesDatabase? = null

        fun getDatabase(context: Context): FavoriteCitiesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FavoriteCitiesDatabase::class.java, "favorite_database")
                    .build().also { Instance = it }
            }
        }
    }

}