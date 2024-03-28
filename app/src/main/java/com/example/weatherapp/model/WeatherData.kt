package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherData(
    @PrimaryKey
    val datetime: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "timezone")
    val timezone: String,

    @ColumnInfo(name = "temp")
    val temp: Double,

    @ColumnInfo(name = "tempmax")
    val tempmax: Double,

    @ColumnInfo(name = "tempmin")
    val tempmin: Double
)