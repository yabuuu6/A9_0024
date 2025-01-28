package com.example.uasbioskop.ui.View.Penayangan


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
import com.example.uasbioskop.model.Penayangan
import com.example.uasbioskop.ui.CostumWidget.TopAppBar
import com.example.uasbioskop.ui.Navigation.DestinasiNavigasi
import com.example.uasbioskop.ui.ViewModel.Penayangan.DetailPenayanganUiState
import com.example.uasbioskop.ui.ViewModel.Penayangan.DetailPenayanganViewModel
import com.example.uasbioskop.ui.viewmodel.PenyediaViewModel

object DestinasiDetailPenayangan : DestinasiNavigasi {
    override val route = "detail_penayangan"
    override val titleRes = "Detail Data Penayangan"
    const val ID_PENAYANGAN = "id_penaynagan"
    val routesWithArg = "$route/{$ID_PENAYANGAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenayanganDetailScreen(
    navigateBack: () -> Unit,
    navigateToPenayanganUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPenayanganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailPenayangan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPenayanganById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPenayanganUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Penayangan"
                )
            }
        }
    ) { innerPadding ->
        PenayanganDetailContent(
            modifier = Modifier.padding(innerPadding),
            detailPenayanganUiState = viewModel.penayanganDetailState
        )
    }
}

@Composable
fun PenayanganDetailContent(
    detailPenayanganUiState: DetailPenayanganUiState,
    modifier: Modifier = Modifier
) {
    if (detailPenayanganUiState is DetailPenayanganUiState.Success) {
        if (detailPenayanganUiState.penayangan.idPenayangan == 0) {
            Box(
                modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { Text("Data tidak ditemukan.") }
        } else {
            ItemDetailPenayangan(
                penayangan = detailPenayanganUiState.penayangan, modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ItemDetailPenayangan(
    modifier: Modifier = Modifier,
    penayangan : Penayangan
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailStudio(judul = "ID Penayangan", isinya = "${penayangan.idPenayangan}")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailStudio(judul = "Nama Film", isinya = "${penayangan.judulFilm}")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailStudio(judul = "Nama Studio", isinya = "${penayangan.namaStudio}")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailStudio(judul = "Tanggal Penayangan", isinya = penayangan.tanggalPenayangan)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailStudio(judul = "Harga Tiket", isinya = penayangan.hargaTiket.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun ComponentDetailStudio(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
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
