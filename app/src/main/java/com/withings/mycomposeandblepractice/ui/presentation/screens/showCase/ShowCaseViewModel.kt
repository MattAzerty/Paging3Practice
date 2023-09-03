package com.withings.mycomposeandblepractice.ui.presentation.screens.showCase

import androidx.lifecycle.ViewModel
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.data.local.SearchPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ShowCaseViewModel @Inject constructor(
private val searchPageRepository: SearchPageRepository,
): ViewModel() {

    fun getSelectedListFlow(): Flow<List<ImageEntity>> {
    return searchPageRepository.selectedImages
    }

    fun onCloseButtonClicked() {
    searchPageRepository.resetSelectedList()
    }

}