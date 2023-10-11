package com.withings.mycomposeandblepractice.ui.presentation.screens.showCase

import android.Manifest
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.withings.mycomposeandblepractice.R
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.ui.presentation.components.clickables.RoundButton
import com.withings.mycomposeandblepractice.ui.theme.DefaultGridImageSize
import com.withings.mycomposeandblepractice.ui.theme.DefaultGridPadding
import com.withings.mycomposeandblepractice.ui.theme.DefaultItemPadding
import com.withings.mycomposeandblepractice.ui.theme.MyComposeAndBLEPracticeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowCaseScreen(
    showCaseEventSharedFlow: SharedFlow<ShowCaseEvent>,
    onNotificationPermissionResult: (result: Boolean) -> Unit,
    imageListFlow: Flow<List<ImageEntity>>,
    onCloseButtonClicked: () -> Unit,
    onDownloadButtonClicked: () -> Unit,
) {

    val imageList = imageListFlow.collectAsState(initial = emptyList())
    val context = LocalContext.current
    var downloadButtonVisible by remember { mutableStateOf(false) }

    val notificationPermissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS) {
            onNotificationPermissionResult(it)
        }

    LaunchedEffect(true) {

        delay(500)
        downloadButtonVisible = true

        showCaseEventSharedFlow.collect { event ->
            when (event) {
                is ShowCaseEvent.AskNotificationPermission -> notificationPermissionState.launchPermissionRequest()
                is ShowCaseEvent.ShowToastMessage -> Toast.makeText(
                    context,
                    context.resources.getString(event.id),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    ShowCaseContent(
        imageList = imageList.value,
        onCloseButtonClicked = onCloseButtonClicked,
        downloadButtonVisible = downloadButtonVisible,
        onDownloadButtonClicked = onDownloadButtonClicked,
    )

}

@Composable
fun ShowCaseContent(
    imageList: List<ImageEntity>,
    onCloseButtonClicked: () -> Unit,
    downloadButtonVisible: Boolean,
    onDownloadButtonClicked: () -> Unit
) {

    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
            //.background(MaterialTheme.colorScheme.background),
        ) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = DefaultGridImageSize),
                contentPadding = PaddingValues(DefaultGridPadding),
                verticalArrangement = Arrangement.spacedBy(DefaultGridPadding),
                horizontalArrangement = Arrangement.spacedBy(DefaultGridPadding),
                modifier = Modifier,

                ) {
                items(imageList, key = { it.id }) { item ->
                    AsyncImage(
                        model = item.webformatURL,
                        contentDescription = "item",
                        modifier = Modifier
                            .height(DefaultGridImageSize),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            RoundButton(
                onButtonClick = onCloseButtonClicked,
                imageVector = Icons.Filled.Close,
            )
        }

        AnimatedVisibility(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Transparent),
            visible = downloadButtonVisible,
            enter = slideInVertically(animationSpec = tween(durationMillis = 100)) {
                // Slide in from 200 dp from the top.
                with(density) { 200.dp.roundToPx() }
            }
        ) {
            Button(
                onClick = {
                    onDownloadButtonClicked()
                },
                modifier = Modifier
                    .padding(DefaultItemPadding)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.download_button),
                    textAlign = TextAlign.Center
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ShowCaseScreenPreview() {
    MyComposeAndBLEPracticeTheme {
        ShowCaseContent(
            imageList = listOf(),
            onCloseButtonClicked = {},
            downloadButtonVisible = false,
            onDownloadButtonClicked = {})
    }
}