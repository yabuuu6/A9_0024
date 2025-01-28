package com.example.uasbioskop.ui.ViewModel.Penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.PenayanganRepository
import com.example.uasbioskop.model.Penayangan
import kotlinx.coroutines.launch

class HomePenayanganViewModel(private val penayanganRepository: PenayanganRepository) : ViewModel() {
    var penayanganList by mutableStateOf<List<Penayangan>>(emptyList())
        private set

    init {
        getPenayangan()
    }

    fun getPenayangan() {
        viewModelScope.launch {
            penayanganList = penayanganRepository.getAllPenayangan().data
        }
    }

    fun deletePenayangan(idPenayangan: Int) {
        viewModelScope.launch {
            penayanganRepository.deletePenayangan(idPenayangan)
            getPenayangan() // Refresh the film list after deletion
        }
    }
}
