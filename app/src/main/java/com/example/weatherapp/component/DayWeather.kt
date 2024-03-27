package com.example.weatherapp.component

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherapp.screens.WeatherViewModel

@Composable
fun DayWeather(viewModel: WeatherViewModel) {
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

    val flag = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!flag.value) {
            flag.value = true
            return
        }else if (viewModel.data.value.loading == true) {
            CircularProgressIndicator()
            return
        }
        else {
            DisplayResults(temp, tempMin, tempMax, datetime, address, timezone, latitude, longitude)
        }
    }
}

@Composable
fun DisplayResults(temp: Double?, tempMin: Double?, tempMax: Double?, datetime: String?, address: String?, timezone: String?, latitude: Double?, longitude: Double?) {
    Card (
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF9AC4F5)
        ),
        elevation = CardDefaults.cardElevation(5.dp),
    ){
        Column (
            modifier = Modifier
                .padding(top = 60.dp, start = 10.dp, end = 10.dp, bottom =0.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "$datetime", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.size(25.dp))
            Surface(modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
                shape = CircleShape,
                shadowElevation = 5.dp,
                tonalElevation = 5.dp,
                color = Color(0xFFFFC400)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "$temp °", style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.ExtraBold)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "$tempMax ° / $tempMin °", style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraLight)
                }
            }
            Spacer(modifier = Modifier.size(25.dp))
            Text(text = "$address ($timezone)", style = MaterialTheme.typography.titleLarge)
        }
    }
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

