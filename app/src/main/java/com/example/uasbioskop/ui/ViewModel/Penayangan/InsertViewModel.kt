package com.example.uasbioskop.ui.ViewModel.Penayangan


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.FilmRepository
import com.example.uasbioskop.Repository.PenayanganRepository
import com.example.uasbioskop.Repository.StudioRepository
import com.example.uasbioskop.model.Penayangan
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InsertPenayanganViewModel(
    private val penayanganRepository: PenayanganRepository,
    private  val studioRepository : StudioRepository,
    private val filmRepository: FilmRepository

): ViewModel() {
    var uiStatePenayangan by mutableStateOf(InsertPenayanganUiState())
        private set

    private val _filmOption = MutableStateFlow<List<String>>(emptyList())
    val filmOption: StateFlow<List<String>> = _filmOption.asStateFlow()

    private val _studioOption = MutableStateFlow<List<String>>(emptyList())
    val studioOption: StateFlow<List<String>> = _studioOption.asStateFlow()

    init {
        fetchFilmOption()
        fetchStudioOption()
    }

    private fun fetchFilmOption() {
        viewModelScope.launch {
            val films = filmRepository.getFilmOption()
            _filmOption.value = films
        }
    }

    private fun fetchStudioOption() {
        viewModelScope.launch {
            val studio = studioRepository.getStudioOption()
            _studioOption.value = studio
        }
    }

    fun updateInsertPenayanganState(insertUiEvent: InsertPenayanganUiEvent) {
        uiStatePenayangan = InsertPenayanganUiState(insertPenayanganUiEvent  = insertUiEvent)
    }

    suspend fun insertPenayanagan() {
        viewModelScope.launch {
            try {
                penayanganRepository.insertPenayangan(uiStatePenayangan.insertPenayanganUiEvent.toPenayangan())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


data class InsertPenayanganUiState(
    val insertPenayanganUiEvent: InsertPenayanganUiEvent = InsertPenayanganUiEvent()
)

data class InsertPenayanganUiEvent(
    val idPenayangan: Int = 0,
    val idFilm: Int = 0,
    val idStudio: Int = 0,
    val tanggalPenayangan: String = "",
    val hargaTiket: Int = 0,
    val judulFilm:String = "",
    val namaStudio: String =""
)

fun InsertPenayanganUiEvent.toPenayangan(): Penayangan = Penayangan(
    idPenayangan = idPenayangan,
    idFilm = idFilm,
    idStudio = idStudio,
    tanggalPenayangan = tanggalPenayangan ,
    hargaTiket = hargaTiket,
    judulFilm = judulFilm,
    namaStudio = namaStudio
)

fun Penayangan.toUiStatePenayangan(): InsertPenayanganUiState = InsertPenayanganUiState(
    insertPenayanganUiEvent = toInsertPenayanganUiEvent()
)

fun Penayangan.toInsertPenayanganUiEvent(): InsertPenayanganUiEvent = InsertPenayanganUiEvent(
    idPenayangan = idPenayangan,
    idFilm = idFilm,
    idStudio = idStudio,
    tanggalPenayangan = tanggalPenayangan,
    hargaTiket = hargaTiket,
    judulFilm = judulFilm.toString(),
    namaStudio = namaStudio.toString()
)