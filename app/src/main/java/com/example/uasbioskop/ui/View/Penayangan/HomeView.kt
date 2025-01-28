package com.example.uasbioskop.ui.View.Penayangan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import com.example.uasbioskop.ui.CostumWidget.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasbioskop.model.Penayangan
import com.example.uasbioskop.ui.Navigation.DestinasiNavigasi
import com.example.uasbioskop.ui.ViewModel.Penayangan.HomePenayanganViewModel
import com.example.uasbioskop.ui.viewmodel.PenyediaViewModel

object DestinasiPenayanganHome : DestinasiNavigasi {
    override val route = "home_penayangan"
    override val titleRes = "Home Penayangan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenayanganScreen(
    navigateHomeBack: () -> Unit,
    navigateToPenayanganEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick:(Int) -> Unit = {},
    viewModel: HomePenayanganViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiPenayanganHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateHomeBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPenayangan()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPenayanganEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Film")
            }
        },

        ) { innerPadding ->
        PenayanganStatus(
            penayangan = viewModel.penayanganList,
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePenayangan(it.idPenayangan)
                viewModel.getPenayangan()
            }
        )
    }
}

@Composable
fun PenayanganStatus(
    penayangan : List<Penayangan>,
    modifier: Modifier = Modifier,
    onDeleteClick:(Penayangan) -> Unit = {},
    onDetailClick: (Int) -> Unit
){
    if (penayangan.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Tidak ada data Penayangan")
        }
    } else {
        PenayanganLayout(
            penayangan = penayangan,
            modifier = modifier.fillMaxWidth(),
            onDetailClick = {
                onDetailClick(it.idPenayangan)
            },
            onDeleteClick = {
                onDeleteClick(it)
            }
        )
    }
}

@Composable
fun PenayanganLayout(
    penayangan: List<Penayangan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Penayangan) -> Unit,
    onDeleteClick: (Penayangan) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(penayangan) { penayangan ->
            PenayanganCard(
                penayangan = penayangan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(penayangan) },
                onDeleteClick = {
                    onDeleteClick(penayangan)
                }
            )
        }
    }
}

@Composable
fun PenayanganCard(
    penayangan: Penayangan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penayangan) -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = penayangan.judulFilm.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = penayangan.idPenayangan.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { deleteConfirmationRequired = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Text(
                text = penayangan.namaStudio.toString(),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = penayangan.tanggalPenayangan,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = penayangan.hargaTiket.toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(penayangan)
            },
            onDeleteCancel =  {
                deleteConfirmationRequired = false
            }, modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDeleteCancel,
        title = {
            Text(
                "Hapus Data",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        text = {
            Text(
                "Apakah Anda yakin ingin menghapus data ini? Tindakan ini tidak dapat dibatalkan.",
                fontSize = 16.sp
            )
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        confirmButton = {
            Button(
                onClick = onDeleteConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Hapus", color = Color.White)
            }
        }
    )
}