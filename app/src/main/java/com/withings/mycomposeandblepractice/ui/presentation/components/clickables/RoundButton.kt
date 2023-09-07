package com.withings.mycomposeandblepractice.ui.presentation.components.clickables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.withings.mycomposeandblepractice.ui.theme.DefaultIconSize
import com.withings.mycomposeandblepractice.ui.theme.DefaultItemPadding
import com.withings.mycomposeandblepractice.ui.theme.MyComposeAndBLEPracticeTheme

@Composable
fun RoundButton(
    onButtonClick: () -> Unit,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.background,
        modifier = modifier
            .size(DefaultIconSize)
            .padding(DefaultItemPadding)
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                shape = CircleShape
            )
            .padding(4.dp)
            .clickable { onButtonClick() }
    )
}

@Preview(showBackground = true)
@Composable
fun RoundButtonPreview() {
    MyComposeAndBLEPracticeTheme {
        RoundButton(
            onButtonClick = {},
            imageVector = Icons.Filled.Close,
        )
    }
}
