package com.example.weatherapp.model

data class Day(
    val datetime: String,
    val datetimeEpoch: Int,
    val temp: Double,
    val tempmax: Double,
    val tempmin: Double
)