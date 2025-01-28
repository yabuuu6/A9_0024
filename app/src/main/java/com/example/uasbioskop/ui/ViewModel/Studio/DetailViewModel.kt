package com.example.uasbioskop.ui.ViewModel.Studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uasbioskop.Repository.StudioRepository
import com.example.uasbioskop.model.Studio
import com.example.uasbioskop.ui.View.Studio.DestinasiDetailStudio
import kotlinx.coroutines.launch
import java.io.IOException


sealed class DetailStudioUiState {
    data class Success(val studio: Studio) : DetailStudioUiState()
    object Error : DetailStudioUiState()
    object Loading : DetailStudioUiState()
}

class DetailStudioViewModel(
    savedStateHandle: SavedStateHandle,
    private val studioRepository: StudioRepository
) : ViewModel() {

    var StudioDetailState: DetailStudioUiState by mutableStateOf(DetailStudioUiState.Loading)
        private set

    private val _idStudio: Int = checkNotNull(savedStateHandle[DestinasiDetailStudio.ID_STUDIO])

    init {
        getStudioById()
    }

    fun getStudioById() {
        viewModelScope.launch {
            StudioDetailState = DetailStudioUiState.Loading
            StudioDetailState = try {
                val studio = studioRepository.getStudioById(_idStudio)
                DetailStudioUiState.Success(studio)
            } catch (e: IOException) {
                DetailStudioUiState.Error
            } catch (e: HttpException) {
                DetailStudioUiState.Error
            }
        }
    }
}