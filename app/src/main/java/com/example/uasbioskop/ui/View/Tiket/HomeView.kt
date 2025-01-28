package com.example.uasbioskop.ui.View.Tiket

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
import com.example.uasbioskop.model.Tiket
import com.example.uasbioskop.ui.Navigation.DestinasiNavigasi
import com.example.uasbioskop.ui.ViewModel.Tiket.HomeTiketViewModel
import com.example.uasbioskop.ui.viewmodel.PenyediaViewModel

object DestinasiTiketHome : DestinasiNavigasi {
    override val route = "home_tiket"
    override val titleRes = "Home Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiketScreen(
    navigateHomeBack: () -> Unit,
    navigateToTiketEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick:(Int) -> Unit = {},
    viewModel: HomeTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiTiketHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateHomeBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTiket()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTiketEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tiket")
            }
        },

        ) { innerPadding ->
        TiketStatus(
            tiket = viewModel.tiketList,
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTiket(it.idTiket)
                viewModel.getTiket()
            }
        )
    }
}

@Composable
fun TiketStatus(
    tiket: List<Tiket>,
    modifier: Modifier = Modifier,
    onDeleteClick:(Tiket) -> Unit = {},
    onDetailClick: (Int) -> Unit
){
    if (tiket.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Tidak ada data Tiket")
        }
    } else {
        TiketLayout(
            tiket = tiket,
            modifier = modifier.fillMaxWidth(),
            onDetailClick = {
                onDetailClick(it.idTiket)
            },
            onDeleteClick = {
                onDeleteClick(it)
            }
        )
    }
}

@Composable
fun TiketLayout(
    tiket: List<Tiket>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tiket) -> Unit,
    onDeleteClick: (Tiket) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tiket) { tiket ->
            TiketCard(
                tiket = tiket,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tiket) },
                onDeleteClick = {
                    onDeleteClick(tiket)
                }
            )
        }
    }
}

@Composable
fun TiketCard(
    tiket: Tiket,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tiket) -> Unit = {}
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
                    text = tiket.idPenayangan.toString(),
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
                text = tiket.statusPembayaran,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = tiket.totalHarga.toString(),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = tiket.jumlahTiket.toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(tiket)
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