package com.withings.mycomposeandblepractice.ui.presentation.components.Images

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.withings.mycomposeandblepractice.data.local.ImageEntity

@Composable
fun ImageItem(
    image: ImageEntity,
    onImageClicked : (ImageEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable{
                onImageClicked(image)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp)
    ) {
        AsyncImage(
            model = image.webformatURL,
            contentDescription = "imageItem",
            modifier = Modifier
                .height(150.dp),
                contentScale = ContentScale.Crop
        )
    }
}