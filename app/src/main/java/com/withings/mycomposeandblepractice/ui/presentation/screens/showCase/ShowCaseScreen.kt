package com.withings.mycomposeandblepractice.ui.presentation.screens.showCase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.ui.presentation.components.clickables.RoundButton
import kotlinx.coroutines.flow.Flow

@Composable
fun ShowCaseScreen(
    imageListFlow: Flow<List<ImageEntity>>,
    onCloseButtonClicked: () -> Unit,
) {
    val imageList = imageListFlow.collectAsState(initial = emptyList())
    val lazyListState = rememberLazyListState()

    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {

        LazyRow(
            state = lazyListState,
            reverseLayout = false,
            modifier = Modifier.padding(4.dp),
            userScrollEnabled = true,
        ) {
            items(imageList.value, key = { it.id }) { item ->
                AsyncImage(
                    model = item.webformatURL,
                    contentDescription = "item",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        RoundButton(
            onButtonClick = onCloseButtonClicked,
            imageVector = Icons.Filled.Close,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}