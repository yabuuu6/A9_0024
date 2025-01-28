package com.example.uasbioskop.ui.ViewModel.Home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.FilmRepository
import com.example.uasbioskop.model.Film
import kotlinx.coroutines.launch

class InsertViewModel(private val filmRepository: FilmRepository): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertFilmState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertFilm() {
        viewModelScope.launch {
            try {
                filmRepository.insertFilm(uiState.insertUiEvent.toFilm())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idFilm: Int = 0,
    val judulFilm: String = "",
    val durasi: Int = 0,
    val deskripsi: String = "",
    val genre: String = "",
    val ratingUsia: String = ""
)

fun InsertUiEvent.toFilm(): Film = Film(
    idFilm = idFilm,
    judulFilm = judulFilm,
    durasi = durasi,
    deskripsi = deskripsi,
    genre = genre,
    ratingUsia = ratingUsia
)

fun Film.toUiStateFilm(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Film.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idFilm = idFilm,
    judulFilm = judulFilm,
    durasi = durasi,
    deskripsi = deskripsi,
    genre = genre,
    ratingUsia = ratingUsia
)
