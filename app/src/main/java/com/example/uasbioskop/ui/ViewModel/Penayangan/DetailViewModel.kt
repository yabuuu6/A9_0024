package com.example.uasbioskop.ui.ViewModel.Penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uasbioskop.Repository.PenayanganRepository
import com.example.uasbioskop.model.Penayangan
import com.example.uasbioskop.ui.View.Penayangan.DestinasiDetailPenayangan
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailPenayanganUiState {
    data class Success(val penayangan: Penayangan) : DetailPenayanganUiState()
    object Error : DetailPenayanganUiState()
    object Loading : DetailPenayanganUiState()
}

class DetailPenayanganViewModel(
    savedStateHandle: SavedStateHandle,
    private val penaynaganRepository: PenayanganRepository
) : ViewModel() {

    var penayanganDetailState: DetailPenayanganUiState by mutableStateOf(DetailPenayanganUiState.Loading)
        private set

    private val _idPenayangan: Int = checkNotNull(savedStateHandle[DestinasiDetailPenayangan.ID_PENAYANGAN])

    init {
        getPenayanganById()
    }

    fun getPenayanganById() {
        viewModelScope.launch {
            penayanganDetailState = DetailPenayanganUiState.Loading
            penayanganDetailState = try {
                val film = penaynaganRepository.getPenayanganById(_idPenayangan)
                DetailPenayanganUiState.Success(film)
            } catch (e: IOException) {
                DetailPenayanganUiState.Error
            } catch (e: HttpException) {
                DetailPenayanganUiState.Error
            }
        }
    }
}
