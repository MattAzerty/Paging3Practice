package com.withings.mycomposeandblepractice.ui.presentation.screens.search

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun SearchImageRoute(
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
        onNextButtonClicked = { searchImageViewModel.onNextButtonClicked() },
        images = searchImageViewModel.imagePagingFlow.collectAsLazyPagingItems(),
        setIsImagesLoading = {searchImageViewModel.setIsImageLoading(it)},
        selectedImagesFlow = searchImageViewModel.getSelectedImages()
    )

}