package com.adriandeseta.weatherapp.data

import com.adriandeseta.weatherapp.data.model.remote.WeatherApiService
import javax.inject.Inject


class WeatherAppRepository @Inject constructor(
    private val apiService: WeatherApiService
) {
    suspend fun getWeatherByCity(city: String) = apiService.getWeatherByCity(
        city,
        apiKey = "486c6d451bd85caa0b3d0d9b2b62a116",
        units = "metric",
        lang = "es"
    )
    suspend fun getWeatherByCoordinates(lat: Double, lon: Double) = apiService.getWeatherByCoordinates(
        lat, lon,
        apiKey = "486c6d451bd85caa0b3d0d9b2b62a116",
        units = "metric",
        lang = "es"
    )
}