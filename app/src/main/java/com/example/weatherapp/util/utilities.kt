package com.example.weatherapp.util
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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