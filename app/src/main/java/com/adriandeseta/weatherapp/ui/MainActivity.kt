package com.adriandeseta.weatherapp.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.adriandeseta.weatherapp.location.LocationService
import com.adriandeseta.weatherapp.ui.weatherapp.WeatherAppScreen
import com.adriandeseta.weatherapp.ui.weatherapp.WeatherAppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: WeatherAppViewModel = hiltViewModel()
            val locationService = remember { LocationService(this) }

            val requestPermissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    viewModel.fetchCurrentLocationWeather(locationService)
                } else {
                    viewModel.fetchWeatherByCity("Buenos Aires") // fallback
                }
            }

            LaunchedEffect(Unit) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            MaterialTheme {
                WeatherAppScreen(viewModel = viewModel)
            }
        }
    }
}
