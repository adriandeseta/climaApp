package com.adriandeseta.weatherapp.ui.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.adriandeseta.weatherapp.R
import com.adriandeseta.weatherapp.data.model.WeatherResponse
import com.adriandeseta.weatherapp.ui.CustomText
import com.adriandeseta.weatherapp.ui.figtreeFontFamily
import kotlin.math.roundToInt

@Composable
fun WeatherCard(data: WeatherResponse) {
    val weather = data.weather.firstOrNull()
    val iconUrl = "https://openweathermap.org/img/wn/${weather?.icon}@2x.png"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomText(
                text = "${data.main.temp.roundToInt()} °C",
                fontSize = 50.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = figtreeFontFamily,
                color = Color.White
            )
            AsyncImage(
                model = iconUrl,
                contentDescription = "Ícono del clima",
                modifier = Modifier.size(64.dp)
            )
        }

        CustomText(
            text = data.name,
            fontSize = 25.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = figtreeFontFamily,
            color = Color.White
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_airwave_24),
                    contentDescription = "Humidity",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    CustomText(
                        text = "${data.main.humidity}%",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = figtreeFontFamily,
                    )
                    CustomText(
                        text = "Humidity",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = figtreeFontFamily,
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_air_24),
                    contentDescription = "Wind Speed",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    CustomText(
                        text = "${data.wind.speed} km/h",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = figtreeFontFamily,
                    )
                    CustomText(
                        text = "Wind Speed",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = figtreeFontFamily,
                    )
                }
            }
        }

    }
}