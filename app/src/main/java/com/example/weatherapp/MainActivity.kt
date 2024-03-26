package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.screens.WeatherViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherHome()
                }
            }
        }
    }
}

@Composable
fun WeatherHome(viewModel: WeatherViewModel = hiltViewModel()) {
    DayWeather(viewModel)
    val address = viewModel.data.value.data?.address
    val timezone = viewModel.data.value.data?.timezone
    val latitude = viewModel.data.value.data?.latitude
    val longitude = viewModel.data.value.data?.longitude

    Log.d("ADDRESS, TIMEZONE", "WeatherHome: $address $timezone")
    Log.d("LATITUDE, LONGITUDE", "WeatherHome: $latitude $longitude")
}

@Composable
fun DayWeather(viewModel: WeatherViewModel) {
    val temp = viewModel.data.value.data?.days?.get(0)?.temp
    val tempMin = viewModel.data.value.data?.days?.get(0)?.tempmin
    val tempMax = viewModel.data.value.data?.days?.get(0)?.tempmax
    val datetime = viewModel.data.value.data?.days?.get(0)?.datetime

    Log.d("TEMP", "DayWeather: $temp")
    Log.d("TEMP MIN", "DayWeather: $tempMin")
    Log.d("TEMP MAX", "DayWeather: $tempMax")
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {

    }
}