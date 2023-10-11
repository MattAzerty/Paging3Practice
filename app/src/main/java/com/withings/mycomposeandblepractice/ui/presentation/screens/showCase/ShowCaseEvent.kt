package com.withings.mycomposeandblepractice.ui.presentation.screens.showCase

sealed class ShowCaseEvent {
    object AskNotificationPermission: ShowCaseEvent()
    data class ShowToastMessage(val id: Int) : ShowCaseEvent()
}