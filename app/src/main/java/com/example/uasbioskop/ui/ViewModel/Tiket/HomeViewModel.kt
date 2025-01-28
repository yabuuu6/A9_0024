package com.example.uasbioskop.ui.ViewModel.Tiket


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.TiketRepository
import com.example.uasbioskop.model.Tiket
import kotlinx.coroutines.launch

class HomeTiketViewModel(private val tiketRepository: TiketRepository) : ViewModel() {
    var tiketList by mutableStateOf<List<Tiket>>(emptyList())
        private set

    init {
        getTiket()
    }

    fun getTiket() {
        viewModelScope.launch {
            tiketList = tiketRepository.getAllTiket().data
        }
    }

    fun deleteTiket(idTiket : Int) {
        viewModelScope.launch {
            tiketRepository.deleteTiket(idTiket)
            getTiket() // Refresh the film list after deletion
        }
    }
}
