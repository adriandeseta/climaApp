package com.adriandeseta.weatherapp.ui.weatherapp

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.adriandeseta.weatherapp.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@RawRes
fun getLottieAnimationForWeather(iconCode: String): Int {
    return when {
        iconCode.startsWith("01") -> R.raw.sunny
        iconCode.startsWith("02") -> R.raw.cloud
        iconCode.startsWith("03") || iconCode.startsWith("04") -> R.raw.cloud
        iconCode.startsWith("09") || iconCode.startsWith("10") -> R.raw.rainy_cloud
        iconCode.startsWith("11") -> R.raw.thunderstorm
        iconCode.startsWith("13") -> R.raw.snowfall
        iconCode.startsWith("50") -> R.raw.cloud
        else -> R.raw.default_weather
    }
}
@Composable
fun LottieAnimationWeather(@RawRes animationRes: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 1f
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .fillMaxSize()
            .zIndex(-1f),
    )
}

@Composable
fun LottieAnimationBackground(@RawRes animationRes: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 1f
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .fillMaxSize()
            .zIndex(-2f),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun LottieAnimationError(@RawRes animationRes: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 1f
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .zIndex(1f).size(300.dp),
    )
}
