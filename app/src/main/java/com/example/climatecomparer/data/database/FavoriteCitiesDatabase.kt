package com.example.climatecomparer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.climatecomparer.data.model.FavoriteCity
import com.example.climatecomparer.data.model.GeoLocationConverter

@Database(
    entities = [FavoriteCity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GeoLocationConverter::class)
abstract class FavoriteCitiesDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteCitiesDao

    companion object {
        @Volatile
        private var Instance: FavoriteCitiesDatabase? = null

        fun getDatabase(context: Context): FavoriteCitiesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FavoriteCitiesDatabase::class.java,
                    "favorite_database"
                ).build().also { Instance = it }
            }
        }
    }
}