package com.adriandeseta.weatherapp.ui.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriandeseta.weatherapp.data.WeatherAppRepository
import com.adriandeseta.weatherapp.location.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherAppViewModel @Inject constructor(
    private val repository: WeatherAppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            try {
                val response = repository.getWeatherByCity(city)
                _uiState.value = WeatherUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(ErrorType.CITY_NOT_FOUND)
            }
        }
    }

    fun fetchWeatherByCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            try {
                val response = repository.getWeatherByCoordinates(lat, lon)
                _uiState.value = WeatherUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(ErrorType.CITY_NOT_FOUND)
            }
        }
    }

    fun fetchWeatherByCity(city: String) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            try {
                val response = repository.getWeatherByCity(city)
                _uiState.value = WeatherUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(ErrorType.CITY_NOT_FOUND)
            }
        }
    }

    fun fetchCurrentLocationWeather(
        locationService: LocationService,
        initial: Boolean = false
    ) {
        viewModelScope.launch {
            if (initial) {
                _uiState.value = WeatherUiState.Empty
                delay(3000L)
            } else {
                _uiState.value = WeatherUiState.Loading
            }

            try {
                val location = locationService.getCurrentLocation()
                if (location != null) {
                    fetchWeatherByCoordinates(location.latitude, location.longitude)
                } else {
                    fetchWeatherByCity("Buenos Aires")
                }
            } catch (e: Exception) {
                fetchWeatherByCity("Buenos Aires")
            }
        }
    }


}
