package com.withings.mycomposeandblepractice.ui.presentation.components.decorations

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun RoundBackgroundGradientForBox(
    modifier: Modifier = Modifier
){

    Box(
        modifier = modifier
            .background(
                Brush.radialGradient(
                    listOf(
                        MaterialTheme.colorScheme.secondary.copy(0.7f),
                        MaterialTheme.colorScheme.background,
                    ),
                    radius = 1500f,
                    center = Offset.Infinite
                )
            )
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(0.1f)),
                shape = CircleShape,
            )
    )

}