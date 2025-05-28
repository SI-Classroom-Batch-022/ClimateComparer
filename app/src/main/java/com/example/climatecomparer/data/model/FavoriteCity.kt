package com.example.climatecomparer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Entity(tableName = "favoriteCities")
data class FavoriteCity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val geoLocation: GeoLocation
)

class GeoLocationConverter {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val adapter = moshi.adapter(GeoLocation::class.java)

    @TypeConverter
    fun fromGeoLocation(geoLocation: GeoLocation): String {
        return adapter.toJson(geoLocation)
    }

    @TypeConverter
    fun toGeoLocation(json: String): GeoLocation? {
        return adapter.fromJson(json)
    }
}