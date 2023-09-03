package com.withings.mycomposeandblepractice.ui.presentation.screens.showCase

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ShowCaseRoute(
    onBackClicked: () -> Unit,
) {
    val showCaseViewModel = hiltViewModel<ShowCaseViewModel>()
    ShowCaseScreen(
        imageListFlow = showCaseViewModel.getSelectedListFlow(),
        onCloseButtonClicked= {
            showCaseViewModel.onCloseButtonClicked()
            onBackClicked()
        }
    )
}