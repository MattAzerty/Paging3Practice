package com.withings.mycomposeandblepractice.ui.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.data.local.SearchPageRepository
import com.withings.mycomposeandblepractice.di.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchImageViewModel @Inject constructor(
    pager: Pager<Int, ImageEntity>,
    private val searchPageRepository: SearchPageRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,

): ViewModel() {

    private val _searchImageEventMutableSharedFlow = MutableSharedFlow<SearchImageEvent>()
    val searchImageEventSharedFlow = _searchImageEventMutableSharedFlow.asSharedFlow()


    val imagePagingFlow = pager
        .flow
        .cachedIn(viewModelScope)


    fun onSearchForImageClicked(fieldOfSearch: String) {
        searchPageRepository.setSearchFieldEntry(fieldOfSearch)
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            _searchImageEventMutableSharedFlow.emit(SearchImageEvent.RefreshList)
        }
        }


    fun onImageClicked(imageEntity: ImageEntity) {
        searchPageRepository.addOrRemoveSelectedImage(imageEntity)
    }

    fun getSelectedImages(): Flow<List<ImageEntity>> {
        return searchPageRepository.selectedImages
    }

}