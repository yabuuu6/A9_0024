package com.example.uasbioskop.ui.ViewModel.Studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.StudioRepository
import com.example.uasbioskop.model.Studio
import kotlinx.coroutines.launch

class InsertStudioViewModel(private val studioRepository: StudioRepository): ViewModel() {
    var uiStateStudio by mutableStateOf(InsertStudioUiState())
        private set

    fun updateInsertStudioState(insertStudioUiEvent: InsertStudioUiEvent) {
        uiStateStudio = InsertStudioUiState(insertStudioUiEvent = insertStudioUiEvent)
    }

    suspend fun insertStudio() {
        viewModelScope.launch {
            try {
                studioRepository.insertStudio(uiStateStudio.insertStudioUiEvent.toStudio())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertStudioUiState(
    val insertStudioUiEvent: InsertStudioUiEvent = InsertStudioUiEvent()
)

data class InsertStudioUiEvent(
    val idStudio: Int = 0,
    val namaStudio : String = "",
    val kapasitas: Int = 0,
)

fun InsertStudioUiEvent.toStudio():Studio = Studio(
    idStudio = idStudio,
    namaStudio = namaStudio,
    kapasitas = kapasitas,

)

fun Studio.toUiStateStudio(): InsertStudioUiState = InsertStudioUiState(
    insertStudioUiEvent = toInsertUiEvent()
)

fun Studio.toInsertUiEvent(): InsertStudioUiEvent = InsertStudioUiEvent(
    idStudio = idStudio,
    namaStudio = namaStudio,
    kapasitas = kapasitas,
)
