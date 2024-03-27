package com.example.weatherapp.screens

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.component.DisplayTemperatures

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

