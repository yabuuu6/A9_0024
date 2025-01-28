package com.example.uasbioskop.ui.View.Tiket

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
import com.example.uasbioskop.model.Tiket
import com.example.uasbioskop.ui.CostumWidget.TopAppBar
import com.example.uasbioskop.ui.Navigation.DestinasiNavigasi
import com.example.uasbioskop.ui.ViewModel.Home.DetailUiState
import com.example.uasbioskop.ui.ViewModel.Home.DetailViewModel
import com.example.uasbioskop.ui.ViewModel.Tiket.DetailTiketUiState
import com.example.uasbioskop.ui.ViewModel.Tiket.DetailTiketViewModel
import com.example.uasbioskop.ui.viewmodel.PenyediaViewModel


object DestinasiTiketDetail : DestinasiNavigasi {
    override val route = "detail_tiket"
    override val titleRes = "Detail Data Tiket"
    const val ID_TIKET = "id_tiket"
    val routesWithArg = "$route/{$ID_TIKET}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiketDetailScreen(
    navigateBack: () -> Unit,
    navigateToTiketUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel:DetailTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiTiketDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTiketById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTiketUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Tiket"
                )
            }
        }
    ) { innerPadding ->
        TiketDetailContent(
            modifier = Modifier.padding(innerPadding),
            detailTiketUiState = viewModel.TiketDetailState
        )
    }
}

@Composable
fun TiketDetailContent(
    detailTiketUiState: DetailTiketUiState,
    modifier: Modifier = Modifier
) {
    if (detailTiketUiState is DetailTiketUiState.Success) {
        if (detailTiketUiState.tiket.idTiket == 0) {
            Box(
                modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { Text("Data tidak ditemukan.") }
        } else {
            ItemDetailTiket(
                tiket = detailTiketUiState.tiket, modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ItemDetailTiket(
    modifier: Modifier = Modifier,
    tiket: Tiket
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailFilm(judul = "ID Penayangan", isinya = "${tiket.idPenayangan}")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailFilm(judul = "Jumlah Tiket", isinya = "${tiket.jumlahTiket}")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailFilm(judul = "Total Harga", isinya = tiket.totalHarga.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailFilm(judul = "Status Pembayaran", isinya = tiket.statusPembayaran)
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
