package com.withings.mycomposeandblepractice.ui.presentation.screens.search

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun SearchImageRoute(
    onNextClicked: () -> Unit
) {
    val searchImageViewModel = hiltViewModel<SearchImageViewModel>()

    SearchImageScreen(
        searchImageEventSharedFlow = searchImageViewModel.searchImageEventSharedFlow,
        onSearchForImageClicked = {fieldOfSearch ->
         searchImageViewModel.onSearchForImageClicked(fieldOfSearch)
        },
        onImageClicked = { imageEntity ->
            searchImageViewModel.onImageClicked(imageEntity)
        },
        onNextButtonClicked = onNextClicked,
        images = searchImageViewModel.imagePagingFlow.collectAsLazyPagingItems(),
        selectedImagesFlow = searchImageViewModel.getSelectedImages()
    )

}