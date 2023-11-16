package com.withings.mycomposeandblepractice.ui.presentation.components.images

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.ui.theme.DefaultElevation
import com.withings.mycomposeandblepractice.ui.theme.DefaultImageSize
import com.withings.mycomposeandblepractice.ui.theme.MyComposeAndBLEPracticeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageItem(
    image: ImageEntity,
    onImageClicked: (ImageEntity) -> Unit,
    onImageLongClick: (ImageEntity) -> Unit,
    modifier: Modifier = Modifier
) {

    var isSelected by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    isSelected = !isSelected
                    onImageClicked(image)
                },
                onLongClick = {
                    onImageLongClick(image)
                }
            )
            .focusRequester(FocusRequester()),
        elevation = CardDefaults.cardElevation(
            defaultElevation = DefaultElevation
        )
    ) {
        AsyncImage(
            model = image.webformatURL,
            contentDescription = "imageItem",
            colorFilter = if (isSelected) ColorFilter.tint(
                MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.6f
                ), blendMode = BlendMode.Hardlight
            ) else null,
            modifier = Modifier
                .height(DefaultImageSize),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImageItemPreview() {
    MyComposeAndBLEPracticeTheme {
        ImageItem(
            image = ImageEntity(
                id = 0,
                webformatURL = ""
            ),
            onImageClicked = {},
            onImageLongClick = {}
        )
    }
}