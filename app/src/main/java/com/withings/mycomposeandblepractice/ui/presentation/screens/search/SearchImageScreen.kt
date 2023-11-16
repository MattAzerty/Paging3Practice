package com.withings.mycomposeandblepractice.ui.presentation.screens.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.withings.mycomposeandblepractice.R
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.ui.presentation.components.clickables.RoundButton
import com.withings.mycomposeandblepractice.ui.presentation.components.clickables.SearchBar
import com.withings.mycomposeandblepractice.ui.presentation.components.decorations.RoundBackgroundGradientForBox
import com.withings.mycomposeandblepractice.ui.presentation.components.images.ImageItem
import com.withings.mycomposeandblepractice.ui.theme.DefaultImageSize
import com.withings.mycomposeandblepractice.ui.theme.DefaultItemPadding
import com.withings.mycomposeandblepractice.ui.theme.MyComposeAndBLEPracticeTheme
import com.withings.mycomposeandblepractice.utils.normalizedItemPosition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlin.math.absoluteValue


@Composable
fun SearchImageScreen(
    searchImageEventSharedFlow: SharedFlow<SearchImageEvent>,
    onSearchForImageClicked: (fieldOfSearch: String) -> Unit,
    onImageClicked: (ImageEntity) -> Unit,
    onImageLongClick: (ImageEntity) -> Unit,
    onNextButtonClicked: () -> Unit,
    images: LazyPagingItems<ImageEntity>,
    setIsImagesLoading: (Boolean) -> Unit,
    selectedImagesFlow: Flow<List<ImageEntity>>
) {


    val lazyListState = rememberLazyListState()
    val context = LocalContext.current
    //snackBar
    val snackBarHostState = remember { SnackbarHostState() }



    LaunchedEffect(key1 = images.loadState) {

        when (images.loadState.refresh) {
            is LoadState.Error -> snackBarHostState.showSnackbar("Error: " + (images.loadState.refresh as LoadState.Error).error.message)
            LoadState.Loading -> lazyListState.scrollToItem(1)//offset in case user want to select first pic}
            is LoadState.NotLoading -> {
                setIsImagesLoading(false)
            }
        }

    }

    val currentImages by rememberUpdatedState(images)

    LaunchedEffect(true) {

        searchImageEventSharedFlow.collect { event ->
            when (event) {
                is SearchImageEvent.RefreshList -> currentImages.refresh()
                is SearchImageEvent.ShowMessage -> snackBarHostState.showSnackbar(
                    context.resources.getString(
                        event.id
                    )
                )
            }
        }

    }

    SearchImageContent(
        images = images,
        lazyListState = lazyListState,
        onImageClicked = onImageClicked,
        onImageLongClick = onImageLongClick,
        onSearchForImageClicked = onSearchForImageClicked,
        selectedImagesFlow = selectedImagesFlow,
        onNextButtonClicked = onNextButtonClicked,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun SearchImageContent(
    images: LazyPagingItems<ImageEntity>,
    lazyListState: LazyListState,
    onImageClicked: (ImageEntity) -> Unit,
    onImageLongClick: (ImageEntity) -> Unit,
    onSearchForImageClicked: (fieldOfSearch: String) -> Unit,
    selectedImagesFlow: Flow<List<ImageEntity>>,
    onNextButtonClicked: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {

    val selectedImages = selectedImagesFlow.collectAsState(initial = emptyList())

    //focus to handle keyboard behavior
    val focusManager = LocalFocusManager.current

    //GraphicLayer
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val widthInDp = configuration.screenWidthDp.dp
    val heightInDp = configuration.screenHeightDp.dp
    val widthInPx = with(density) { widthInDp.roundToPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (images.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {//Show search screen components

            RoundBackgroundGradientForBox(
                modifier = Modifier
                    .requiredHeight(heightInDp / 1.5f)
                    .requiredWidth(heightInDp / 1.5f)
                    .align(Alignment.Center)
                    .offset(x = -widthInDp / 2)
                    .graphicsLayer {
                        clip = true
                        shape = CircleShape
                    }
            )
            //Paging3 https://developer.android.com/jetpack/androidx/releases/paging
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(DefaultItemPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                item { Spacer(modifier = Modifier.height(DefaultImageSize)) }

                items(
                    count = images.itemCount,
                    key = images.itemKey { it.id },
                ) { index ->
                    val image = images[index]
                    if (image != null) {
                        ImageItem(
                            image = image,
                            onImageClicked = {
                                focusManager.clearFocus()
                                onImageClicked(it)
                            },
                            onImageLongClick = {
                                focusManager.clearFocus()
                                onImageLongClick(it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer {
                                    val varPos =
                                        (lazyListState.layoutInfo.normalizedItemPosition(
                                            image.id
                                        )).absoluteValue
                                    scaleX =
                                        1 - (varPos * 0.1F)//scale 0.9..1 btwn center on X
                                    scaleY =
                                        1 - (varPos * 0.1F)//scale 0.9..1 btwn center on X
                                    translationX = -8.dp.toPx() +
                                            ((widthInPx * 0.12F - DefaultItemPadding.toPx()) * varPos)//translation to right
                                },
                        )
                    }

                }



                item {
                    if (images.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }

            //For Preview see ui/presentation/components/clickable/SearchBar.kt
            SearchBar(
                modifier = Modifier.padding(top = DefaultImageSize / 3f),
                focusManager = focusManager,
                onSearch = {
                    onSearchForImageClicked(it)
                }
            )

            ItemPagerCountIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                imagesCount = images.itemCount,
            )



            when {
                selectedImages.value.size >= 2 -> {
                    Button(
                        onClick = onNextButtonClicked,
                        modifier = Modifier
                            .padding(DefaultItemPadding)
                            .align(Alignment.BottomEnd)
                    ) {
                        Text(text = stringResource(R.string.next))
                    }
                }

                images.itemCount <= 1 -> {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomEnd),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        RoundButton(
                            onButtonClick = {},
                            imageVector = Icons.Filled.Close,
                        )
                    }

                }
            }


            SnackbarHost(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                hostState = snackBarHostState,
                snackbar = {
                    Snackbar(
                        containerColor = MaterialTheme.colorScheme.primary,
                        snackbarData = it
                    )
                }
            )

        }
    }

}


@Composable
fun ItemPagerCountIndicator(
    modifier: Modifier = Modifier,
    imagesCount: Int) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(
            modifier = Modifier.padding(DefaultItemPadding),
            color = MaterialTheme.colorScheme.primary.copy(0.7f),
            text = imagesCount.toString(),
            style = TextStyle(
                fontSize = 40.sp,
                shadow = Shadow(
                    color = Color.Black.copy(0.3f),
                    offset = Offset(7.0f, 4.0f),
                    blurRadius = 0.5f
                )
            )
        )
    }

}


@Preview(showBackground = true)
@Composable
fun RoundBackgroundGradientForBoxPreview() {
    MyComposeAndBLEPracticeTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .wrapContentSize()){
        RoundBackgroundGradientForBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.Center)
                .graphicsLayer {
                    clip = true
                    shape = CircleShape
                }
        )
        }
}}

@Preview(showBackground = true)
@Composable
fun ItemPagerCountIndicatorPreview() {
    MyComposeAndBLEPracticeTheme {
        ItemPagerCountIndicator( imagesCount = 20)
    }
}

@Preview(showBackground = true)
@Composable
fun SearchImageScreenPreview() {
    //https://issuetracker.google.com/issues/194544557 (Q2/Q3 2023 for preview)
    val fakeData = List(10) { ImageEntity(id = it, webformatURL = "") }
    val pagingData = PagingData.from(fakeData)
    val fakeDataFlow = MutableStateFlow(pagingData)

    MyComposeAndBLEPracticeTheme {
        SearchImageContent(
            fakeDataFlow.collectAsLazyPagingItems(),
            rememberLazyListState(),
            {},
            {},
            {},
            MutableStateFlow(fakeData),
            {},
            SnackbarHostState(),
        )
    }
}
