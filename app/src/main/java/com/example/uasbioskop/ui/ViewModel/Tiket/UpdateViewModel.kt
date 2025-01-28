package com.example.uasbioskop.ui.ViewModel.Tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.PenayanganRepository
import com.example.uasbioskop.Repository.TiketRepository
import com.example.uasbioskop.model.Penayangan
import com.example.uasbioskop.model.Tiket
import com.example.uasbioskop.ui.View.Tiket.DestinasiTiketUpdate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


import kotlinx.coroutines.launch

class UpdateTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tiketRepository: TiketRepository,
    private val penayanganRepository : PenayanganRepository
) : ViewModel() {
    var updateTiketUiState by mutableStateOf(InsertTiketUiState())
        private set

    var penayanganList by mutableStateOf(listOf<Penayangan>())
        private set

    private val _idTiket: Int = checkNotNull(savedStateHandle[DestinasiTiketUpdate.ID_TIKET])

    init {
        viewModelScope.launch {
            loadData()
            loadTiket(_idTiket)
        }

    }

    private suspend fun loadData(){
        penayanganList = penayanganRepository.getPenayanganOption()
    }

    private fun loadTiket(idTiket: Int){
        viewModelScope.launch {
            try {
                val tiket = tiketRepository.getTiketById(idTiket)
                updateTiketUiState = tiket.toUiStateTiket()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }


    fun updateInsertTiketState(insertTiketUiEvent: InsertTiketUiEvent) {
        updateTiketUiState = InsertTiketUiState(insertTiketUiEvent = insertTiketUiEvent)
    }

    suspend fun updateTiket() {
        viewModelScope.launch {
            try {
                tiketRepository.updateTiket(_idTiket, updateTiketUiState.insertTiketUiEvent.toTiket())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
