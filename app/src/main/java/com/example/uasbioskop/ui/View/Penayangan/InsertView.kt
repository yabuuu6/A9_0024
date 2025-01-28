package com.example.uasbioskop.ui.View.Penayangan

import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasbioskop.ui.CostumWidget.TopAppBar
import com.example.uasbioskop.ui.Navigation.DestinasiNavigasi
import com.example.uasbioskop.ui.ViewModel.Penayangan.InsertPenayanganViewModel
import com.example.uasbioskop.ui.ViewModel.Penayangan.InsertPenayanganUiEvent
import com.example.uasbioskop.ui.ViewModel.Penayangan.InsertPenayanganUiState
import kotlinx.coroutines.launch
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import com.example.uasbioskop.ui.viewmodel.PenyediaViewModel

object DestinasiPenayanganEntry : DestinasiNavigasi {
    override val route = "penayangan_entry"
    override val titleRes = "Isi Data Penayangan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenayanganEntryScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenayanganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val filmOption by viewModel.filmOption.collectAsState()
    val studioOption by viewModel.studioOption.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiPenayanganEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertPenayanganUiState = viewModel.uiStatePenayangan,
            onPenayanganValueChange = viewModel::updateInsertPenayanganState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPenayanagan()
                    navigateBack()
                }
            },
            filmOption = filmOption,
            studioOption = studioOption,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertPenayanganUiState: InsertPenayanganUiState,
    onPenayanganValueChange: (InsertPenayanganUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    filmOption: List<String>,
    studioOption: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertPenayanganUiEvent = insertPenayanganUiState.insertPenayanganUiEvent,
            onValueChange = onPenayanganValueChange,
            filmOption = filmOption,
            studioOption = studioOption,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertPenayanganUiEvent: InsertPenayanganUiEvent,
    onValueChange: (InsertPenayanganUiEvent) -> Unit,
    filmOption: List<String>,
    studioOption: List<String>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var expandedFilm by remember { mutableStateOf(false) }
    var selectedFilmOption by remember { mutableStateOf(filmOption.getOrNull(0) ?: "") }

    var expandedStudio by remember { mutableStateOf(false) }
    var selectedStudioOption by remember { mutableStateOf(studioOption.getOrNull(0) ?: "") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expandedFilm,
            onExpandedChange = { expandedFilm = !expandedFilm }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedFilmOption,
                onValueChange = {},
                label = { Text("Pilih Film") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFilm)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedFilm,
                onDismissRequest = { expandedFilm = false }
            ) {
                filmOption.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedFilmOption = selectionOption
                            expandedFilm = false
                            onValueChange(insertPenayanganUiEvent.copy(idFilm = selectionOption.split(" - ")[0].toInt()))
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = expandedStudio,
            onExpandedChange = { expandedStudio = !expandedStudio }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedStudioOption,
                onValueChange = {},
                label = { Text("Pilih Studio") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStudio)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedStudio,
                onDismissRequest = { expandedStudio = false }
            ) {
                studioOption.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedStudioOption = selectionOption
                            expandedStudio = false
                            onValueChange(insertPenayanganUiEvent.copy(idStudio = selectionOption.split(" - ")[0].toInt()))
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = insertPenayanganUiEvent.tanggalPenayangan,
            onValueChange = { onValueChange(insertPenayanganUiEvent.copy(tanggalPenayangan = it)) },
            label = { Text("Masukan Tanggal Penayangan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPenayanganUiEvent.hargaTiket.toString(),
            onValueChange = { onValueChange(insertPenayanganUiEvent.copy(hargaTiket = it.toIntOrNull() ?: 0)) },
            label = { Text("Masukan Harga Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
