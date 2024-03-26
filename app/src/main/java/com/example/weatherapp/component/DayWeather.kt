package com.example.weatherapp.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.screens.WeatherViewModel

@Composable
fun DayWeather(viewModel: WeatherViewModel) {



//    Log.d("ADDRESS, TIMEZONE", "WeatherHome: $address $timezone")
//    Log.d("LATITUDE, LONGITUDE", "WeatherHome: $latitude $longitude")

//    Log.d("TEMP", "DayWeather: $temp")
//    Log.d("TEMP MIN", "DayWeather: $tempMin")
//    Log.d("TEMP MAX", "DayWeather: $tempMax")


        WeatherDisplay(viewModel)





}

@Composable
fun DisplayTemperatures(viewModel: WeatherViewModel) {
    val temp = viewModel.data.value.data?.days?.get(0)?.temp
    val tempMin = viewModel.data.value.data?.days?.get(0)?.tempmin
    val tempMax = viewModel.data.value.data?.days?.get(0)?.tempmax
    val datetime = viewModel.data.value.data?.days?.get(0)?.datetime
    val address = viewModel.data.value.data?.address
    val timezone = viewModel.data.value.data?.timezone
    val latitude = viewModel.data.value.data?.latitude
    val longitude = viewModel.data.value.data?.longitude
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Date: $datetime")
        Text(text ="Address: $address")
        Text(text = "Timezone: $timezone")
        Text(text = "Latitude: $latitude")
        Text(text = "Longitude: $longitude")
        Text(text = "Temperature: $temp")
        Text(text = "Minimum Temperature: $tempMin")
        Text(text = "Maximum Temperature: $tempMax")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun WeatherDisplay(viewModel: WeatherViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
//            .padding(4.dp)
    ) {
        Scaffold (
            topBar = {
                TopAppBar(title = { Text(text = "Weather App") }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF9AC4F5),

                ),
                        actions = {
                    Icon(imageVector = Icons.Rounded.Favorite, contentDescription = "Icon")
                }
                )
            }
        ) {
                innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
                ,
            ) {
                MainContent(viewModel)
                DisplayTemperatures(viewModel)
            }
        }

    }
}

@Composable
fun MainContent(viewModel: WeatherViewModel) {
    val date = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth()
//        .padding(14.dp),
//            value = "",
//            onValueChange = {},
//            label = { Text("Enter City") }
//        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            value = date.value,
            onValueChange = {date.value = it},
            label = { Text("Enter Date in YYYY-MM-DD") },
            enabled = true,
            readOnly = false
        )

        Button(onClick = {
            viewModel.getWeatherData(date.value)
            date.value = ""
        }) {
            Text(text = "Search")
        }

    }
}

