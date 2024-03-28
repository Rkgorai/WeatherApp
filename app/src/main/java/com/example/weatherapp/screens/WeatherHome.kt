package com.example.weatherapp.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.component.DisplayResults
import com.example.weatherapp.component.DisplayTemperatures
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.getPreviousYears
import com.example.weatherapp.util.isFutureDate
import com.example.weatherapp.util.isValidDate
import com.example.weatherapp.util.isValidDateFormat

@Composable
fun WeatherHome(viewModel: WeatherViewModel = hiltViewModel()) {
    WeatherDisplay(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
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
//                DisplayTemperatures(viewModel)
            }
        }

    }
}

@Composable
fun MainContent(viewModel: WeatherViewModel) {

    val weatherDataList = viewModel.weatherList.collectAsState().value

    val date = remember {
        mutableStateOf("")
    }
    val dateCopy = remember {
        mutableStateOf("")
    }
    val error = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            value = date.value,
            onValueChange = {
                date.value = it
                dateCopy.value = it
                error.value = ""
            },
            label = { Text("Enter Date in YYYY-MM-DD") },
            enabled = true,
            readOnly = false
        )

        val isFromDB = remember {
            mutableStateOf(false)
        }

        val isFuture = remember {
            mutableStateOf(false)
        }

        Button(onClick = {
            if (date.value.isEmpty()) {
                error.value = "Please enter a date"
                return@Button
            }

            if(!isValidDateFormat(date.value)){
                error.value = "Please enter a valid date format"
                return@Button
            }

            if (!isValidDate(date.value)) {
                error.value = "Please enter a valid date"
                return@Button
            }

            val weatherDataForDate = weatherDataList.find { it.datetime == date.value }
            if (weatherDataForDate != null) {
                isFromDB.value = true
                Log.d("BUTTON", "WeatherDataForDate: $weatherDataForDate")

            } else{
                if(isFutureDate(date.value)){

                    /*TODO*/
                    viewModel.getWeatherDataForYears(getPreviousYears(date.value, 10), date.value)
                    viewModel.getWeatherData(date.value)
                    isFuture.value = true

                    Log.d("BUTTON", "MainContent: Date is in the future")
                }else {
                    viewModel.getWeatherData(date.value)
                }
            }
            date.value = ""
        }) {
            Text(text = "Search")
        }

        if (error.value.isNotEmpty()) {
            Text(text = error.value, color = Color.Red)
        }

        Log.d("TAG", "WeatherList: ${viewModel.weatherList.collectAsState().value}")
        if (isFromDB.value) {
            weatherDataList.find { it.datetime == dateCopy.value }
                ?.let { DisplayResults(weatherData = it) }
            Log.d("FROM DB", "MainContent: FROM DB ${dateCopy.value}")
        }
        else if(isFuture.value){
            Log.d("INSIDE Future", "Object: ${viewModel.obj}")
            DisplayResults(weatherData = viewModel.obj.value)
        }
        else {
            DisplayTemperatures(viewModel)
        }
    }
}
