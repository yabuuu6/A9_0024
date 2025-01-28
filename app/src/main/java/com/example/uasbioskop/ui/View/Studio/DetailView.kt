package com.example.uasbioskop.ui.View.Studio

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
import com.example.uasbioskop.model.Studio
import com.example.uasbioskop.ui.CostumWidget.TopAppBar
import com.example.uasbioskop.ui.Navigation.DestinasiNavigasi
import com.example.uasbioskop.ui.ViewModel.Studio.DetailStudioUiState
import com.example.uasbioskop.ui.ViewModel.Studio.DetailStudioViewModel
import com.example.uasbioskop.ui.viewmodel.PenyediaViewModel

object DestinasiDetailStudio : DestinasiNavigasi {
    override val route = "detail_studio"
    override val titleRes = "Detail Data Studio"
    const val ID_STUDIO = "id_studio"
    val routesWithArg = "$route/{$ID_STUDIO}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioDetailScreen(
    navigateBack: () -> Unit,
    navigateToStudioUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailStudioViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailStudio.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getStudioById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToStudioUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Studio"
                )
            }
        }
    ) { innerPadding ->
        StudioDetailContent(
            modifier = Modifier.padding(innerPadding),
            detailStudioUiState = viewModel.StudioDetailState
        )
    }
}

@Composable
fun StudioDetailContent(
    detailStudioUiState: DetailStudioUiState,
    modifier: Modifier = Modifier
) {
    if (detailStudioUiState is DetailStudioUiState.Success) {
        if (detailStudioUiState.studio.idStudio == 0) {
            Box(
                modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { Text("Data tidak ditemukan.") }
        } else {
            ItemDetailStudio(
                studio = detailStudioUiState.studio, modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ItemDetailStudio(
    modifier: Modifier = Modifier,
    studio : Studio
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailStudio(judul = "Nama Studio", isinya = studio.namaStudio)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailStudio(judul = "Kapasitas", isinya = "${studio.kapasitas}")
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
