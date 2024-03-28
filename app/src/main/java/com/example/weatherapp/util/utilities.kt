package com.example.weatherapp.util
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun getPreviousYears(inputDate: String, years: Int): List<String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(inputDate, formatter)

    val previousYears = mutableListOf<String>()

    var current = date.year - 1 // Start from the year before the input year

    repeat(years) {
        if (current < LocalDate.now().year) {
            previousYears.add(LocalDate.of(current, date.month, date.dayOfMonth).format(formatter))
        }
        current--
    }

    return previousYears
}

fun isFutureDate(dateString: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(dateString, formatter)
    val currentDate = LocalDate.now()

    return date.isAfter(currentDate)
}

fun isValidDateFormat(date: String): Boolean {
    val dateRegex = Regex("""\d{4}-\d{2}-\d{2}""")
    return date.matches(dateRegex)
}

fun isValidDate(dateString: String): Boolean {
    return try {
        // Attempt to parse the input string as a LocalDate
        LocalDate.parse(dateString)
        true
    } catch (e: DateTimeParseException) {
        // Catch the exception if parsing fails, indicating an invalid date format
        false
    }
}

fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}
