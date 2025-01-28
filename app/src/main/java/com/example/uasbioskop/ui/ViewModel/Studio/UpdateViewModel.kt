package com.example.uasbioskop.ui.ViewModel.Studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.StudioRepository
import com.example.uasbioskop.ui.View.Studio.DestinasiStudioUpdate
import kotlinx.coroutines.launch

class UpdateStudioViewModel(
    savedStateHandle: SavedStateHandle,
    private val studioRepository: StudioRepository
) : ViewModel() {
    var updateStudioUiState by mutableStateOf(InsertStudioUiState())
        private set

    private val _idStudio: Int = checkNotNull(savedStateHandle[DestinasiStudioUpdate.ID_STUDIO])

    init {
        viewModelScope.launch {
            updateStudioUiState = studioRepository.getStudioById(_idStudio)
                .toUiStateStudio()
        }
    }

    fun updateInsertStudioState(insertStudioUiEvent: InsertStudioUiEvent) {
        updateStudioUiState = InsertStudioUiState(insertStudioUiEvent = insertStudioUiEvent)
    }

    suspend fun updateStudio() {
        viewModelScope.launch {
            try {
                studioRepository.updateStudio(_idStudio, updateStudioUiState.insertStudioUiEvent.toStudio())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
