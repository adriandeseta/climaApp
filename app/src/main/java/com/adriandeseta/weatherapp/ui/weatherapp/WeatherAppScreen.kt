package com.adriandeseta.weatherapp.ui.weatherapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.adriandeseta.weatherapp.R
import com.adriandeseta.weatherapp.data.model.WeatherResponse

@Composable
fun WeatherAppScreen(viewModel: WeatherAppViewModel) {
    var city by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimationBackground(R.raw.default_weather)

        Box(modifier = Modifier.fillMaxSize()) {

            val animationRes = when (state) {
                is WeatherUiState.Success -> {
                    val iconCode =
                        (state as WeatherUiState.Success).data.weather.firstOrNull()?.icon ?: "01d"
                    getLottieAnimationForWeather(iconCode)
                }

                else -> R.raw.cloud
            }
            LottieAnimationWeather(animationRes)

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (state) {
                    is WeatherUiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is WeatherUiState.Success -> {
                        WeatherCard((state as WeatherUiState.Success).data)
                    }

                    is WeatherUiState.Error -> {
                        Text(
                            (state as WeatherUiState.Error).message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    WeatherUiState.Empty -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 48.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "¡Bienvenido a WeatherApp!",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ingresá una ciudad para ver el clima.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }

                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Ciudad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { viewModel.fetchWeather(city) }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.fetchWeather(city) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Buscar clima")
                }

                Spacer(modifier = Modifier.height(24.dp))


            }
        }
    }
}

// UI State
sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val data: WeatherResponse) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
    object Empty : WeatherUiState()
}
