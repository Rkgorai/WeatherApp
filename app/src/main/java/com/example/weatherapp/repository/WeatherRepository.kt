package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherAPI) {
    private val dataOrException = DataOrException<WeatherResponse, Boolean, Exception>()

    suspend fun getWeatherData(endDate: String = "today"): DataOrException<WeatherResponse, Boolean, Exception> {

        try {
            dataOrException.loading = true
            dataOrException.data = api.getWeatherData(endDate)
            if (dataOrException.data.toString().isNotEmpty()) {
                dataOrException.loading = false
            }
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.d("Exc", "getWeatherData: $exception")
        }
        return dataOrException
    }
}
