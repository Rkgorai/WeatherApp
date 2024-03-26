package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface WeatherAPI {
    @GET("${Constants.city}/{startDate}/{endDate}?unitGroup=metric&elements=datetime%2CdatetimeEpoch%2Cname%2Caddress%2CresolvedAddress%2Clatitude%2Clongitude%2Ctempmax%2Ctempmin%2Ctemp&include=days&key=${Constants.API_KEY}&contentType=json")
    suspend fun getWeatherData(
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String = "today"
    ): WeatherResponse
}