package com.withings.mycomposeandblepractice.ui.presentation.screens.showCase

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.withings.mycomposeandblepractice.ui.presentation.screens.search.SearchImageScreen

@Composable
fun ShowCaseRoute(
) {
    val showCaseViewModel = hiltViewModel<ShowCaseViewModel>()
    ShowCaseScreen(
        imageListFlow = showCaseViewModel.getImageList()
    )
}