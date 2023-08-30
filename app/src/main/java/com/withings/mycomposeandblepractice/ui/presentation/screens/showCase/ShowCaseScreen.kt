package com.withings.mycomposeandblepractice.ui.presentation.screens.showCase

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import kotlinx.coroutines.flow.Flow

@Composable
fun ShowCaseScreen(
    imageListFlow: Flow<List<ImageEntity>>,
) {
    val imageList = imageListFlow.collectAsState(initial = emptyList())
    val lazyListState = rememberLazyListState()

    LazyRow(
        state = lazyListState,
        reverseLayout = true,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = true,
    ) {
        items(imageList.value, key = { it.id }) { item ->
            //val index = it % galleryUIViewState.pictures.size
            //val picture = galleryUIViewState.pictures[index]
            AsyncImage(
                model = item.webformatURL,
                contentDescription = "item",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
        }
    }
}