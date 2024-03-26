package com.example.weatherapp.model

data class Day(
    val datetime: String,
    val datetimeEpoch: Int,
    val temp: Double,
    val tempmax: Double,
    val tempmin: Double
)
/*
* {
  "queryCost": 1,
  "latitude": 28.631,
  "longitude": 77.2172,
  "resolvedAddress": "New Delhi, DL, India",
  "address": "New Delhi",
  "timezone": "Asia/Kolkata",
  "tzoffset": 5.5,
  "days": [
    {
      "datetime": "2024-03-26",
      "datetimeEpoch": 1711391400,
      "tempmax": 36.7,
      "tempmin": 18,
      "temp": 27.8
    }
  ]
}*/