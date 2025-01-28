package com.example.uasbioskop.ui.ViewModel.Tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uasbioskop.Repository.TiketRepository
import com.example.uasbioskop.model.Tiket
import com.example.uasbioskop.ui.View.Tiket.DestinasiTiketDetail

import kotlinx.coroutines.launch
import java.io.IOException


sealed class DetailTiketUiState {
    data class Success(val tiket: Tiket) : DetailTiketUiState()
    object Error : DetailTiketUiState()
    object Loading : DetailTiketUiState()
}

class DetailTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tiketRepository: TiketRepository
) : ViewModel() {

    var TiketDetailState: DetailTiketUiState by mutableStateOf(DetailTiketUiState.Loading)
        private set

    private val _idTiket: Int = checkNotNull(savedStateHandle[DestinasiTiketDetail.ID_TIKET])

    init {
        getTiketById()
    }

    fun getTiketById() {
        viewModelScope.launch {
            TiketDetailState = DetailTiketUiState.Loading
            TiketDetailState = try {
                val tiket = tiketRepository.getTiketById(_idTiket)
                DetailTiketUiState.Success(tiket)
            } catch (e: IOException) {
                DetailTiketUiState.Error
            } catch (e: HttpException) {
                DetailTiketUiState.Error
            }
        }
    }
}