package com.example.uasbioskop.ui.View.Film

import com.example.uasbioskop.ui.ViewModel.Home.DetailUiState
import com.example.uasbioskop.ui.ViewModel.Home.DetailViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasbioskop.model.Film
import com.example.uasbioskop.ui.CostumWidget.TopAppBar
import com.example.uasbioskop.ui.Navigation.DestinasiNavigasi
import com.example.uasbioskop.ui.viewmodel.PenyediaViewModel

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Data Film"
    const val ID_FILM = "id_film"
    val routesWithArg = "$route/{$ID_FILM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getFilmById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Film"
                )
            }
        }
    ) { innerPadding ->
        FilmDetailContent(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.filmDetailState
        )
    }
}

@Composable
fun FilmDetailContent(
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier
) {
    if (detailUiState is DetailUiState.Success) {
        if (detailUiState.film.idFilm == 0) {
            Box(
                modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { Text("Data tidak ditemukan.") }
        } else {
            ItemDetailFilm(
                film = detailUiState.film, modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ItemDetailFilm(
    modifier: Modifier = Modifier,
    film: Film
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailFilm(judul = "Judul Film", isinya = film.judulFilm)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailFilm(judul = "Durasi", isinya = "${film.durasi} menit")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailFilm(judul = "Deskripsi", isinya = film.deskripsi)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailFilm(judul = "Genre", isinya = film.genre)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailFilm(judul = "Rating Usia", isinya = film.ratingUsia)
        }
    }
}

@Composable
fun ComponentDetailFilm(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

