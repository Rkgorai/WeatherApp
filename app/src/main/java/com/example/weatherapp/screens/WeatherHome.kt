package com.example.weatherapp.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.component.DayWeather

@Composable
fun WeatherHome(viewModel: WeatherViewModel = hiltViewModel()) {
    DayWeather(viewModel)


}
