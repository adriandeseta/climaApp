package com.adriandeseta.weatherapp.ui.weatherapp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adriandeseta.weatherapp.R
import com.adriandeseta.weatherapp.data.model.WeatherResponse
import com.adriandeseta.weatherapp.location.LocationService
import com.adriandeseta.weatherapp.ui.CustomText
import com.adriandeseta.weatherapp.ui.coinyFontFamily
import com.adriandeseta.weatherapp.ui.figtreeFontFamily
import kotlinx.coroutines.delay


@Composable
fun WeatherAppScreen(
    viewModel: WeatherAppViewModel,
    locationService: LocationService
) {
    var city by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        delay(4000L)
    }

    val errorMessage = when (val uiState = state) {
        is WeatherUiState.Error -> {
            when (uiState.errorType) {
                ErrorType.CITY_NOT_FOUND -> stringResource(R.string.weatherapp_error_message)
                ErrorType.LOCATION_ERROR -> stringResource(R.string.weatherapp_error_message_cordenates)
                ErrorType.UNKNOWN -> stringResource(R.string.weatherapp_error_message_cordenates)
            }
        }

        else -> ""
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimationBackground(R.raw.default_weather)

        Box(modifier = Modifier.fillMaxSize()) {

            if (state is WeatherUiState.Success) {
                val iconCode =
                    (state as WeatherUiState.Success).data.weather.firstOrNull()?.icon ?: "01d"
                val animationRes = getLottieAnimationForWeather(iconCode)
                LottieAnimationWeather(animationRes)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state !is WeatherUiState.Empty) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            textStyle = TextStyle(
                                fontFamily = figtreeFontFamily,
                                fontSize = 18.sp
                            ),
                            value = city,
                            onValueChange = { city = it },
                            placeholder = { Text(stringResource(R.string.weatherapp_search_placeholder)) },
                            singleLine = true,
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .padding(end = 8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White.copy(
                                    alpha = 0.4f
                                ),
                                unfocusedContainerColor = Color.White.copy(
                                    alpha = 0.4f
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
                                    color = Color.White.copy(alpha = 0.4f),
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.weatherapp_search_content_description),
                                tint = Color.White
                            )
                        }
                    }
                }
                AnimatedContent(
                    targetState = state,
                    label = "WeatherStateTransition",
                    transitionSpec = {
                        (fadeIn(tween(500)) togetherWith fadeOut(tween(500)))
                            .using(SizeTransform(clip = false))
                    }
                ) { state ->
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
                            WeatherCard(state.data)
                        }

                        is WeatherUiState.Error -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CustomText(
                                    text = errorMessage,
                                    color = Color.Red,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = figtreeFontFamily,
                                    fontSize = 30.sp
                                )
                                Spacer(Modifier.height(20.dp))

                                LottieAnimationError(R.raw.error)

                                Spacer(Modifier.height(24.dp))

                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    onClick = {
                                        viewModel.fetchCurrentLocationWeather(
                                            locationService
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White.copy(alpha = 0.4f),
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    CustomText(
                                        text = stringResource(R.string.weatherapp_button_back),
                                        fontFamily = figtreeFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 24.sp,
                                        color = Color.White
                                    )
                                }
                            }

                        }

                        WeatherUiState.Empty -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CustomText(
                                    fontWeight = FontWeight.Normal,
                                    text = stringResource(R.string.weatherpp_welcome_title_head),
                                    fontSize = 45.sp,
                                    fontFamily = coinyFontFamily,
                                    color = Color.White
                                )
                                CustomText(
                                    fontWeight = FontWeight.Normal,
                                    text = stringResource(R.string.weatherpp_welcome_title_body),
                                    fontSize = 45.sp,
                                    fontFamily = coinyFontFamily,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                CustomText(
                                    fontWeight = FontWeight.Normal,
                                    text = stringResource(R.string.weatherpp_welcome_sub_title_head),
                                    fontSize = 25.sp,
                                    fontFamily = figtreeFontFamily,
                                    color = Color.White
                                )
                                CustomText(
                                    fontWeight = FontWeight.Normal,
                                    text = stringResource(R.string.weatherpp_welcome_sub_title_body),
                                    fontSize = 25.sp,
                                    fontFamily = figtreeFontFamily,
                                    color = Color.White
                                )
                            }
                        }

                    }
                }

            }
        }
    }
}

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    object Empty : WeatherUiState()
    data class Success(val data: WeatherResponse) : WeatherUiState()
    data class Error(val errorType: ErrorType) : WeatherUiState()
}

enum class ErrorType {
    CITY_NOT_FOUND,
    LOCATION_ERROR,
    UNKNOWN
}