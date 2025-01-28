package com.example.uasbioskop.ui.ViewModel.Studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.StudioRepository
import com.example.uasbioskop.model.Studio
import kotlinx.coroutines.launch

class HomeStudioViewModel(private val studioRepository: StudioRepository) : ViewModel() {
    var studioList by mutableStateOf<List<Studio>>(emptyList())
        private set

    init {
        getStudio()
    }

    fun getStudio() {
        viewModelScope.launch {
            studioList = studioRepository.getAllStudio().data
        }
    }

    fun deleteStudio(idStudio: Int) {
        viewModelScope.launch {
            studioRepository.deleteStudio(idStudio)
            getStudio() // Refresh the film list after deletion
        }
    }
}
