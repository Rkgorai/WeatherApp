package com.example.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.model.WeatherData

@Database(entities = [WeatherData::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDatabaseDao
}