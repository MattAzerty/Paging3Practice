package com.withings.mycomposeandblepractice.ui.presentation.screens.search

sealed class SearchImageEvent {
    object RefreshList: SearchImageEvent()
}