package com.example.uasbioskop.ui.ViewModel.Tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.PenayanganRepository
import com.example.uasbioskop.Repository.TiketRepository
import com.example.uasbioskop.model.Tiket
import com.example.uasbioskop.model.Penayangan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InsertTiketViewModel(
    private val tiketRepository: TiketRepository,
    private val penayanganRepository: PenayanganRepository
) : ViewModel() {

    var uiStateTiket by mutableStateOf(InsertTiketUiState())
        private set
    var penayanganList by mutableStateOf(listOf<Penayangan>())
        private set

    private val _penayanganOption = MutableStateFlow<List<String>>(emptyList())
    val penayanganOption: StateFlow<List<String>> = _penayanganOption.asStateFlow()

    init {
        viewModelScope.launch {
            loadData()
        }

    }



    private suspend fun loadData(){
            penayanganList = penayanganRepository.getAllPenayangan().data
            penayanganList.forEach { penayangan ->
                println("Penayangan: ${penayangan.idPenayangan}")
            }
    }

    fun updateInsertTiketState(insertTiketUiEvent: InsertTiketUiEvent) {
        val selectedPenayangan = penayanganList.find { it.idPenayangan == insertTiketUiEvent.idPenayangan }
        val jumlahTiket = insertTiketUiEvent.jumlahTiket
        val hargaTiket = selectedPenayangan?.hargaTiket ?: 0
        val totalHarga = jumlahTiket * hargaTiket

        uiStateTiket = uiStateTiket.copy(
            insertTiketUiEvent = insertTiketUiEvent.copy(
                totalHarga = totalHarga
            )
        )
    }

    fun insertTiket() {
        viewModelScope.launch {
            try {
                tiketRepository.insertTiket(uiStateTiket.insertTiketUiEvent.toTiket())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTiketUiState(
    val insertTiketUiEvent: InsertTiketUiEvent = InsertTiketUiEvent()
)

data class InsertTiketUiEvent(
    val idTiket: Int = 0,
    val idPenayangan: Int = 0,
    val jumlahTiket: Int = 0,
    val totalHarga: Int = 0,
    val statusPembayaran: String = "",
)

fun InsertTiketUiEvent.toTiket(): Tiket = Tiket(
    idTiket = idTiket,
    idPenayangan = idPenayangan,
    jumlahTiket = jumlahTiket,
    totalHarga = totalHarga,
    statusPembayaran = statusPembayaran,
)

fun Tiket.toUiStateTiket(): InsertTiketUiState = InsertTiketUiState(
    insertTiketUiEvent = toInsertUiEvent()
)

fun Tiket.toInsertUiEvent(): InsertTiketUiEvent = InsertTiketUiEvent(
    idTiket = idTiket,
    idPenayangan = idPenayangan,
    jumlahTiket = jumlahTiket,
    totalHarga = totalHarga,
    statusPembayaran = statusPembayaran,
)
