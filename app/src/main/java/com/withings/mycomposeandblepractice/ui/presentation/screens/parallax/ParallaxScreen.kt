package com.withings.mycomposeandblepractice.ui.presentation.screens.parallax

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.withings.mycomposeandblepractice.data.local.SensorData
import com.withings.mycomposeandblepractice.ui.presentation.components.images.ParallaxImageCard
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ParallaxScreen(
    initSensorManager: () -> Unit,
    cancelSensorManager: () -> Unit,
    sensorDataStateFlow: StateFlow<SensorData>,
    picturePath: String,
    onBackIconClicked: () -> Unit,
){

    DisposableEffect(Unit) {

    initSensorManager()

    onDispose {
        cancelSensorManager()
        }

    }

    ParallaxImageCard(
        picturePath,
        sensorDataStateFlow,
        onBackIconClicked
    )

}