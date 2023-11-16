package com.withings.mycomposeandblepractice.ui.presentation.screens.parallax

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ParallaxRoute(
    picturePath: String
) {
    val parallaxViewModel = hiltViewModel<ParallaxViewModel>()

    ParallaxScreen(
        picturePath = picturePath,
        initSensorManager = { parallaxViewModel.initSensorManager() },
        cancelSensorManager = { parallaxViewModel.cancelSensorManager() },
        sensorDataStateFlow = parallaxViewModel.sensorDataStateFlow,
        onBackIconClicked = {parallaxViewModel.onBackIconClicked()}
    )
}