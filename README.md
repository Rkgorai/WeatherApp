# Weather Application

This is a weather application built with Kotlin and Java, using Gradle as a build tool. The application fetches and displays weather data for multiple years. It is designed to handle network errors and exceptions gracefully and provides a function to check the network connectivity status.

## Features

- Fetch and display weather data for multiple years: The application fetches weather data for multiple years concurrently using Kotlin coroutines and displays the average temperature, minimum temperature, and maximum temperature for the fetched years.

- Handle network errors and exceptions: The application includes error handling to ensure that it doesn't crash when there's a network error or an exception while fetching the weather data.

- Check network connectivity status: The application includes a function to check whether the network is connected or not. This function is used to decide whether to fetch weather data or display a "No network connection" message.

- Database: The application uses Room database to store the fetched weather data locally. This allows the application to display weather data even when there's no network connection.

- API: The application uses Retrofit to fetch weather data from a REST API. The fetched data is then parsed into data classes using Moshi.

## Code Structure

The code is organized into several packages:

- `com.example.weatherapp`: Contains the main activity of the application and the application class.

- `com.example.weatherapp.model`: Contains data classes that represent the weather data.

- `com.example.weatherapp.screens`: Contains the ViewModel and the Composable function for the main screen of the application.

- `com.example.weatherapp.util`: Contains utility functions, including functions to check the network connectivity status and to get the previous years from a given date.

- `com.example.weatherapp.data`: Contains the Room database and the DAO (Data Access Object) for accessing the database.

- `com.example.weatherapp.network`: Contains the Retrofit service and the adapters for fetching and parsing the weather data.

## Future Work

The application is a work in progress. Future enhancements may include adding more detailed weather information, improving the user interface, and adding more error handling.
