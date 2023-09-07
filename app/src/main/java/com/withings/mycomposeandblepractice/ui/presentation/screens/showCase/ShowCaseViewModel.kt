package com.withings.mycomposeandblepractice.ui.presentation.screens.showCase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.gson.Gson
import com.withings.mycomposeandblepractice.R
import com.withings.mycomposeandblepractice.data.local.DownloadPictureWorker
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.data.local.SearchPageRepository
import com.withings.mycomposeandblepractice.di.CoroutineDispatcherProvider
import com.withings.mycomposeandblepractice.di.PermissionChecker
import com.withings.mycomposeandblepractice.utils.KEY_INPUT_DATA_WORK_MANAGER_REF_PIC_LIST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowCaseViewModel @Inject constructor(
    private val workManager: WorkManager,
    private val searchPageRepository: SearchPageRepository,
    private val permissionChecker: PermissionChecker,
    private val gson: Gson,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    private val _showCaseEventMutableSharedFlow = MutableSharedFlow<ShowCaseEvent>()
    val showCaseEventSharedFlow = _showCaseEventMutableSharedFlow.asSharedFlow()

    fun getSelectedListFlow(): Flow<List<ImageEntity>> {
        return searchPageRepository.selectedImages
    }

    fun onCloseButtonClicked() {
        searchPageRepository.resetSelection()
    }

    fun onDownloadButtonClicked() {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            if (permissionChecker.hasNotificationPermission()) {
                // 1 - send downloading to worker
                val imageEntitiesAsJson = gson.toJson(searchPageRepository.selectedImages.value)
                workManager.enqueue(
                    OneTimeWorkRequestBuilder<DownloadPictureWorker>()
                        .setInputData(workDataOf(KEY_INPUT_DATA_WORK_MANAGER_REF_PIC_LIST to imageEntitiesAsJson))
                        .build()
                )
                //2 - quit showcase
                _showCaseEventMutableSharedFlow.emit(ShowCaseEvent.ReturnToSearchScreen)
            } else {
                _showCaseEventMutableSharedFlow.emit(ShowCaseEvent.AskNotificationPermission)
            }
        }
    }

    fun onNotificationPermissionResult(hasNotificationPermission: Boolean) {
        if (hasNotificationPermission) {
            onDownloadButtonClicked()
        } else {
            viewModelScope.launch(coroutineDispatcherProvider.io) {
                _showCaseEventMutableSharedFlow.emit(ShowCaseEvent.ShowToastMessage(R.string.downloading_images))
                onDownloadButtonClicked()
            }
        }
    }

}