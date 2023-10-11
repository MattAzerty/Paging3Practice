package com.withings.mycomposeandblepractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.withings.mycomposeandblepractice.data.local.SearchPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    searchPageRepository: SearchPageRepository,
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = searchPageRepository.isImagesLoading.map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.LoadingImages,
        started = SharingStarted.WhileSubscribed(5_000),
    )


}
sealed interface MainActivityUiState {
    object LoadingImages : MainActivityUiState
    data class Success(val test: Boolean) : MainActivityUiState
}