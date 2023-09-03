package com.withings.mycomposeandblepractice.ui.presentation.components.Images

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.ui.theme.DefaultImageSize

@Composable
fun ImageItem(
    image: ImageEntity,
    onImageClicked : (ImageEntity) -> Unit,
    modifier: Modifier = Modifier
) {

    var isSelected by rememberSaveable { mutableStateOf(false) }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .focusable()
            .clickable {
                isSelected = !isSelected
                onImageClicked(image)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp)
    ) {
        AsyncImage(
            model = image.webformatURL,
            contentDescription = "imageItem",
            colorFilter = if(isSelected) ColorFilter.tint(MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), blendMode = BlendMode.Hardlight) else null,
            modifier = Modifier
                .height(DefaultImageSize),
                contentScale = ContentScale.Crop
        )
    }
}