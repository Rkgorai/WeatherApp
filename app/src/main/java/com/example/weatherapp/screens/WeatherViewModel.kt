package com.example.weatherapp.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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