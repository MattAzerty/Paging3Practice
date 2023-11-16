package com.withings.mycomposeandblepractice.ui.presentation.components.clickables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ClickableIcon(
    modifier: Modifier,
    iconId: Int,
    colorTint: Color,
    iconSize: Dp,
    onIconClicked: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            //.wrapContentSize()
            .clickable(
                onClick = onIconClicked,
                enabled = true,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colorTint,
                    bounded = false,
                )
            ),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconId),
            contentDescription = "Settings",
            modifier = Modifier
                .padding(20.dp)
                .size(iconSize),
            tint = colorTint
        )
    }
}