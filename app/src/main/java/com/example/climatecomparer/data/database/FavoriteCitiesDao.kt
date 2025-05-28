package com.example.climatecomparer.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.climatecomparer.data.model.FavoriteCity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCitiesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteCity: FavoriteCity)

    @Query("SELECT * FROM favoriteCities ORDER BY id ASC")
    fun getAllFavoriteCities(): Flow<List<FavoriteCity>>

    @Delete
    suspend fun delete(favoriteCity: FavoriteCity)

    @Update
    suspend fun update(favoriteCity: FavoriteCity)

    @Query("DELETE FROM favoriteCities WHERE geoLocation = :geoLocationJson")
    suspend fun deleteByGeoLocation(geoLocationJson: String)

    @Query("SELECT * FROM favoriteCities WHERE geoLocation = :geoLocationJson LIMIT 1")
    suspend fun findByGeoLocation(geoLocationJson: String): FavoriteCity?
}
