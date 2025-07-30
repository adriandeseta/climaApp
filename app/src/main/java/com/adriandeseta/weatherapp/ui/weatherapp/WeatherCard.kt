package com.adriandeseta.weatherapp.ui.weatherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adriandeseta.weatherapp.data.model.WeatherResponse

@Composable
fun WeatherCard(data: WeatherResponse) {
    val weather = data.weather.firstOrNull()
    val iconUrl = "https://openweathermap.org/img/wn/${weather?.icon}@2x.png"

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.4f)
        ),
        modifier = Modifier
            .fillMaxWidth()
        ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Ciudad: ${data.name}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Temperatura: ${data.main.temp} °C")
                    Text(text = "Clima: ${weather?.description ?: "N/A"}")
                    Text(text = "Humedad: ${data.main.humidity} %")
                    Text(text = "Viento: ${data.wind.speed} m/s")
                }

                AsyncImage(
                    model = iconUrl,
                    contentDescription = "Ícono del clima",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}
