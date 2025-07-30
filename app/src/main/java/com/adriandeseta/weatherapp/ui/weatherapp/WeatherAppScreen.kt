package com.adriandeseta.weatherapp.ui.weatherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.adriandeseta.weatherapp.R
import com.adriandeseta.weatherapp.data.model.WeatherResponse
import kotlinx.coroutines.delay


@Composable
fun WeatherAppScreen(viewModel: WeatherAppViewModel) {
    var city by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()
    var hasLaunched by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(4000L) // 4 segundos
    }
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        placeholder = { Text("Search") },
                        singleLine = true,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(end = 8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White.copy(
                                alpha = 0.5f
                            ),
                            unfocusedContainerColor = Color.White.copy(
                                alpha = 0.5f
                            ),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            focusedPlaceholderColor = Color.White,
                            unfocusedPlaceholderColor = Color.White
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { viewModel.fetchWeather(city) }
                        )
                    )

                    IconButton(
                        onClick = { viewModel.fetchWeather(city) },
                        modifier = Modifier
                            .size(56.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.White
                        )
                    }
                }
                when (state) {
                    is WeatherUiState.Loading -> {
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                        }

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

                Spacer(modifier = Modifier.height(16.dp))

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
