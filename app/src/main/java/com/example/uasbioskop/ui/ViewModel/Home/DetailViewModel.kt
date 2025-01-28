package com.example.uasbioskop.ui.ViewModel.Home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uasbioskop.Repository.FilmRepository
import com.example.uasbioskop.model.Film
import com.example.uasbioskop.ui.View.Film.DestinasiDetail
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailUiState {
    data class Success(val film: Film) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val filmRepository: FilmRepository
) : ViewModel() {

    var filmDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _idFilm: Int = checkNotNull(savedStateHandle[DestinasiDetail.ID_FILM])

    init {
        getFilmById()
    }

    fun getFilmById() {
        viewModelScope.launch {
            filmDetailState = DetailUiState.Loading
            filmDetailState = try {
                val film = filmRepository.getFilmById(_idFilm)
                DetailUiState.Success(film)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }
}
