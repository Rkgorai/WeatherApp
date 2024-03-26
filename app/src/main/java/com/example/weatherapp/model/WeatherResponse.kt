package com.example.weatherapp.model

data class WeatherResponse(
    val address: String,
    val days: List<Day>,
    val latitude: Double,
    val longitude: Double,
    val queryCost: Int,
    val resolvedAddress: String,
    val timezone: String,
    val tzoffset: Double
)