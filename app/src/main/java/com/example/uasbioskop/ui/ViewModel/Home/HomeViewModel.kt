package com.example.uasbioskop.ui.ViewModel.Home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasbioskop.Repository.FilmRepository
import com.example.uasbioskop.model.Film
import kotlinx.coroutines.launch

class HomeViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    var filmList by mutableStateOf<List<Film>>(emptyList())
        private set

    init {
        getFilms()
    }

    fun getFilms() {
        viewModelScope.launch {
            filmList = filmRepository.getAllFilm().data
        }
    }

    fun deleteFilm(idFilm: Int) {
        viewModelScope.launch {
            filmRepository.deleteFilm(idFilm)
            getFilms() // Refresh the film list after deletion
        }
    }
}
