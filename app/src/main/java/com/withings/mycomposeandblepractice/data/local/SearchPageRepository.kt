package com.withings.mycomposeandblepractice.data.local

import com.withings.mycomposeandblepractice.utils.PIXABAY_STARTING_PAGE_INDEX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchPageRepository @Inject constructor(
) {

    private val _isImagesLoading:MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isImagesLoading = _isImagesLoading.asStateFlow()

    private val _selectedImages = MutableStateFlow<List<ImageEntity>>(emptyList())
    val selectedImages = _selectedImages.asStateFlow()

    fun addOrRemoveSelectedImage(imageEntity: ImageEntity) {
        val updatedList = if (_selectedImages.value.contains(imageEntity)) {
            _selectedImages.value - imageEntity
        } else {
            _selectedImages.value + imageEntity
        }
        _selectedImages.value = updatedList
    }

    private val _currentPageFlow = MutableStateFlow(PIXABAY_STARTING_PAGE_INDEX)
    val currentPageFlow = _currentPageFlow.asStateFlow()

    fun setNewPage(){
        _currentPageFlow.value ++
    }

    private val _searchFlow:MutableStateFlow<String> = MutableStateFlow("")
    val searchFlow = _searchFlow.asStateFlow()

    fun setSearchFieldEntry(field:String){
        _searchFlow.value = field
        _currentPageFlow.value = PIXABAY_STARTING_PAGE_INDEX
    }

    fun resetSelection() {
        _selectedImages.value = emptyList()
        _searchFlow.value =""
        _currentPageFlow.value = PIXABAY_STARTING_PAGE_INDEX
    }

    fun setIsImageLoading(imageLoading: Boolean) {
        _isImagesLoading.value = imageLoading
    }

}