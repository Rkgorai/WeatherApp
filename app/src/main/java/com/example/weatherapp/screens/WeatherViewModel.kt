package com.example.weatherapp.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    val data : MutableState<DataOrException<WeatherResponse, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception(""))
    )

    init {
        getWeatherData("today")
    }

    private fun getWeatherData(startDate: String, endDate: String = "today") {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getWeatherData(startDate, endDate)
            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
            }
        }
    }
}