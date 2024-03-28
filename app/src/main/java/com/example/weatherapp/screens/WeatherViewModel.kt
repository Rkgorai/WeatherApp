package com.example.weatherapp.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.component.DisplayResults
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.*
import java.text.DecimalFormat

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    val data : MutableState<DataOrException<WeatherResponse, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception(""))
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllWeatherData().distinctUntilChanged().collect {listOfWeatherData ->
                if (listOfWeatherData.isNullOrEmpty()) {
                    Log.d("Empty", ": Empty List")
                }
                else {
                    _weatherList.value = listOfWeatherData
                }
            }
        }
//        getWeatherData(endDate="today")
    }

//    var obj: WeatherData? = null
val obj = mutableStateOf(WeatherData(
    datetime = "",
    temp = 0.0,
    tempmin = 0.0,
    tempmax = 0.0,
    address = "",
    timezone = "",
    latitude = 0.0,
    longitude = 0.0
))
    fun getWeatherData(endDate: String) {
        viewModelScope.launch {
            // Clear the previous data
            data.value = DataOrException(null, true, Exception(""))

            //Fetch New Data
            data.value.loading = true
            data.value = repository.getWeatherData(endDate)
            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
            }
            addWeatherData(WeatherData(datetime = data.value.data?.days?.get(0)?.datetime.toString(), temp = data.value.data?.days?.get(0)?.temp!!, tempmin = data.value.data?.days?.get(0)?.tempmin!!, tempmax = data.value.data?.days?.get(0)?.tempmax!!, address = data.value.data?.address!!, timezone = data.value.data?.timezone!!, latitude = data.value.data?.latitude!!, longitude = data.value.data?.longitude!!))
        }
    }
    val df = DecimalFormat("#.##")

    // Define a suspend function to fetch weather data for a list of years
    suspend fun fetchWeatherDataForYears(years: List<String>, d: String) {
        withContext(Dispatchers.Default) {
            // Start asynchronous tasks concurrently for each year in the list
            val deferredResults = years.map { year ->
                async {
                    repository.getWeatherData(year)
                }
            }

            // Wait for all tasks to complete
            val results = deferredResults.awaitAll()

            // Process the results
            val allSuccessful: Boolean = results.all { it.data != null}
            if (allSuccessful) {
                // Your code when all responses are successful
                // Process the results
                val temperatures = results.flatMap { it.data?.days?.map { day -> day.temp } ?: emptyList() }
                val averageTemperature = temperatures.average()

                val minTemperatures = results.flatMap { it.data?.days?.map { day -> day.tempmin } ?: emptyList() }
                val averageMinTemperature = minTemperatures.average()

                val maxTemperatures = results.flatMap { it.data?.days?.map { day -> day.tempmax } ?: emptyList() }
                val averageMaxTemperature = minTemperatures.average()

                val addresses = results.map { it.data?.address ?: "" }
                val timezones = results.map { it.data?.timezone ?: "" }
                val latitudes = results.map { it.data?.latitude ?: 0.0 }
                val longitudes = results.map { it.data?.longitude ?: 0.0 }
                val datetime = results.map { it.data?.days?.get(0)?.datetime ?: "" }

                obj.value = WeatherData(datetime = d, temp = String.format("%.2f", averageTemperature).toDouble(), tempmin = String.format("%.2f", averageMinTemperature).toDouble(), tempmax = String.format("%.2f", averageMaxTemperature).toDouble(), address = addresses[0], timezone = timezones[0], latitude = latitudes[0], longitude = longitudes[0])
            } else {
                // Your code when at least one response is not successful
                // Handle the error
//                val error = results.firstOrNull { it.data == null }?.exception
                Log.e("Error", "Error fetching weather data: ")
            }
        }
    }

    fun getWeatherDataForYears(years: List<String>, d: String) {
        // Launch a coroutine to call the fetchWeatherDataForYears function
        viewModelScope.launch {
            try {
                fetchWeatherDataForYears(years, d)
            } catch (e: Exception) {
                // Handle any exceptions that might occur during the API call
                Log.e("WeatherData", "Error fetching weather data for years: ${e.message}", e)
            }
        }
    }



    private val _weatherList = MutableStateFlow<List<WeatherData>>(emptyList())
    val weatherList = _weatherList.asStateFlow()

     fun addWeatherData(weatherData: WeatherData) = viewModelScope.launch {
        repository.addWeatherData(weatherData)
    }

    fun searchWeatherData(date: String) = viewModelScope.launch {
        repository.searchWeatherDataByDate(date)
    }

     fun removeWeatherData(weatherData: WeatherData) = viewModelScope.launch {
        repository.deleteWeatherData(weatherData)
    }

}