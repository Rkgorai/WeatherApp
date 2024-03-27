package com.example.weatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.WeatherData
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDatabaseDao {

    @Query("SELECT * FROM weather_table")
    fun getAllWeatherData(): Flow<List<WeatherData>>

    @Query("SELECT * FROM weather_table WHERE datetime = :datetime")
    suspend fun getWeatherData(datetime: String): WeatherData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherData: WeatherData)

    @Query("DELETE FROM weather_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(weatherData: WeatherData)

}
