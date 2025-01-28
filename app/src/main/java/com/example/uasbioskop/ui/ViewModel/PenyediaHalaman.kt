package com.example.uasbioskop.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uasbioskop.FilmApplication
import com.example.uasbioskop.ui.ViewModel.Home.DetailViewModel
import com.example.uasbioskop.ui.ViewModel.Home.HomeViewModel
import com.example.uasbioskop.ui.ViewModel.Home.InsertViewModel
import com.example.uasbioskop.ui.ViewModel.Home.UpdateViewModel
import com.example.uasbioskop.ui.ViewModel.Penayangan.DetailPenayanganViewModel
import com.example.uasbioskop.ui.ViewModel.Penayangan.HomePenayanganViewModel
import com.example.uasbioskop.ui.ViewModel.Penayangan.InsertPenayanganViewModel
import com.example.uasbioskop.ui.ViewModel.Penayangan.UpdatePenayanganViewModel
import com.example.uasbioskop.ui.ViewModel.Studio.DetailStudioViewModel
import com.example.uasbioskop.ui.ViewModel.Studio.HomeStudioViewModel
import com.example.uasbioskop.ui.ViewModel.Studio.InsertStudioViewModel
import com.example.uasbioskop.ui.ViewModel.Studio.UpdateStudioViewModel
import com.example.uasbioskop.ui.ViewModel.Tiket.DetailTiketViewModel
import com.example.uasbioskop.ui.ViewModel.Tiket.HomeTiketViewModel
import com.example.uasbioskop.ui.ViewModel.Tiket.InsertTiketViewModel
import com.example.uasbioskop.ui.ViewModel.Tiket.UpdateTiketViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiFilm().container.filmRepository) }
        initializer { InsertViewModel(aplikasiFilm().container.filmRepository) }
        initializer { DetailViewModel(createSavedStateHandle(), aplikasiFilm().container.filmRepository) }
        initializer { UpdateViewModel(createSavedStateHandle(), aplikasiFilm().container.filmRepository) }
        // Studio ViewModels
        initializer { HomeStudioViewModel(aplikasiFilm().container.studioRepository) }
        initializer { InsertStudioViewModel(aplikasiFilm().container.studioRepository) }
        initializer { DetailStudioViewModel(createSavedStateHandle(), aplikasiFilm().container.studioRepository) }
        initializer { UpdateStudioViewModel(createSavedStateHandle(), aplikasiFilm().container.studioRepository) }
        // Tiket ViewModels
        initializer { HomeTiketViewModel(aplikasiFilm().container.tiketRepository) }
        initializer { InsertTiketViewModel(
            aplikasiFilm().container.tiketRepository,
            aplikasiFilm().container.penayanganRepository,

        ) }
        initializer { DetailTiketViewModel(createSavedStateHandle(), aplikasiFilm().container.tiketRepository) }
        initializer { UpdateTiketViewModel(createSavedStateHandle(),
            aplikasiFilm().container.tiketRepository,
            aplikasiFilm().container.penayanganRepository
        ) }
        // Penayangan ViewModels

        initializer { HomePenayanganViewModel(aplikasiFilm().container.penayanganRepository) }
        initializer { InsertPenayanganViewModel(aplikasiFilm().container.penayanganRepository, aplikasiFilm().container.studioRepository , aplikasiFilm().container.filmRepository) }
        initializer { DetailPenayanganViewModel(createSavedStateHandle(), aplikasiFilm().container.penayanganRepository) }
        initializer { UpdatePenayanganViewModel(
            createSavedStateHandle(),
            aplikasiFilm().container.penayanganRepository,
            aplikasiFilm().container.studioRepository,
            aplikasiFilm().container.filmRepository
        ) }


    }

    fun CreationExtras.aplikasiFilm(): FilmApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FilmApplication)
}
