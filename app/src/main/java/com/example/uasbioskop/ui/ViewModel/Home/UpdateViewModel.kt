package com.example.uasbioskop.ui.ViewModel.Home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.FilmRepository
import com.example.uasbioskop.ui.View.Film.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val filmRepository: FilmRepository
) : ViewModel() {
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    private val _idFilm: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID_FILM])

    init {
        viewModelScope.launch {
            updateUiState = filmRepository.getFilmById(_idFilm)
                .toUiStateFilm()
        }
    }

    fun updateInsertFilmState(insertUiEvent: InsertUiEvent) {
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateFilm() {
        viewModelScope.launch {
            try {
                filmRepository.updateFilm(_idFilm, updateUiState.insertUiEvent.toFilm())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
