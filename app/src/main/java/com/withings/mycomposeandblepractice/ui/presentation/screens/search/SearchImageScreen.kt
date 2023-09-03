package com.withings.mycomposeandblepractice.ui.presentation.screens.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import androidx.paging.compose.items
import com.withings.mycomposeandblepractice.ui.presentation.components.Images.ImageItem
import com.withings.mycomposeandblepractice.ui.presentation.components.clickables.SearchBar
import kotlinx.coroutines.flow.SharedFlow
import androidx.compose.ui.res.stringResource
import com.withings.mycomposeandblepractice.R
import com.withings.mycomposeandblepractice.ui.theme.DefaultImageSize
import com.withings.mycomposeandblepractice.ui.theme.DefaultPadding
import kotlinx.coroutines.flow.Flow


@Composable
fun SearchImageScreen(
    searchImageEventSharedFlow: SharedFlow<SearchImageEvent>,
    onSearchForImageClicked: (fieldOfSearch: String) -> Unit,
    onImageClicked: (ImageEntity) -> Unit,
    onNextButtonClicked: () -> Unit,
    images: LazyPagingItems<ImageEntity>,
    selectedImagesFlow: Flow<List<ImageEntity>>
) {

    val selectedImages = selectedImagesFlow.collectAsState(initial = emptyList())
    val lazyListState = rememberLazyListState()


    val context = LocalContext.current
    LaunchedEffect(key1 = images.loadState) {
        if(images.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (images.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }else{
            lazyListState.scrollToItem(1)//offset in case user want to select first pic
        }
    }

    val currentImages by rememberUpdatedState(images)

    LaunchedEffect(true) {

        searchImageEventSharedFlow.collect { event ->
            when(event){
                is SearchImageEvent.RefreshList -> currentImages.refresh()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if(images.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {

            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = DefaultPadding),
                verticalArrangement = Arrangement.spacedBy(DefaultPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {Spacer(modifier = Modifier.height(DefaultImageSize))}


                items(images) { image ->
                    if(image != null) {
                        ImageItem(
                            image = image,
                            onImageClicked = onImageClicked,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
                item {
                    if(images.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }

            SearchBar(
                modifier = Modifier.padding(top = 50.dp),
                onSearch = {
                    onSearchForImageClicked(it)
                }
            )
            if(selectedImages.value.size>=2) {
                Button(
                    onClick = onNextButtonClicked,
                    modifier = Modifier
                        .padding(DefaultPadding)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(text = stringResource(R.string.next))
                }
            }
        }
    }
}