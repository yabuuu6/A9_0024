package com.example.uasbioskop.ui.ViewModel.Penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.FilmRepository
import com.example.uasbioskop.Repository.PenayanganRepository
import com.example.uasbioskop.Repository.StudioRepository
import com.example.uasbioskop.ui.View.Penayangan.DestinasiPenayanganUpdate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdatePenayanganViewModel(
    savedStateHandle: SavedStateHandle,
    private val penayanganRepository: PenayanganRepository,
    private val studioRepository: StudioRepository,
    private val filmRepository: FilmRepository
) : ViewModel() {
    var updatePenayanganUiState by mutableStateOf(InsertPenayanganUiState())
        private set

    private val _filmOption = MutableStateFlow<List<String>>(emptyList())
    val filmOption: StateFlow<List<String>> = _filmOption.asStateFlow()


    private val _studioOption = MutableStateFlow<List<String>>(emptyList())
    val studioOption: StateFlow<List<String>> = _studioOption.asStateFlow()

    private val _idPenayangan: Int = checkNotNull(savedStateHandle[DestinasiPenayanganUpdate.ID_PENAYANGAN])

    init {
        viewModelScope.launch {
            updatePenayanganUiState = penayanganRepository.getPenayanganById(_idPenayangan)
                .toUiStatePenayangan()
        }
        fetchFilmOption()
        fetchStudioOption()
    }
    private fun fetchFilmOption() {
        viewModelScope.launch {
            val film = filmRepository.getFilmOption()
            _filmOption.value = film
        }
    }

    private fun fetchStudioOption() {
        viewModelScope.launch {
            val studio = studioRepository.getStudioOption()
            _studioOption.value = studio
        }
    }

    fun updateInsertPenayanganState(insertPenayanganUiEvent: InsertPenayanganUiEvent) {
        updatePenayanganUiState = InsertPenayanganUiState(insertPenayanganUiEvent = insertPenayanganUiEvent)
    }

    suspend fun updatePenayangan() {
        viewModelScope.launch {
            try {
                penayanganRepository.updatePenayangan(_idPenayangan, updatePenayanganUiState.insertPenayanganUiEvent.toPenayangan())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
