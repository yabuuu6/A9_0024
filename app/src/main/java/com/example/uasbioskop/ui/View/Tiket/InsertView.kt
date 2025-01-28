package com.example.uasbioskop.ui.View.Tiket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasbioskop.model.Penayangan
import com.example.uasbioskop.ui.CostumWidget.TopAppBar
import com.example.uasbioskop.ui.Navigation.DestinasiNavigasi
import com.example.uasbioskop.ui.ViewModel.Tiket.InsertTiketUiEvent
import com.example.uasbioskop.ui.ViewModel.Tiket.InsertTiketUiState
import com.example.uasbioskop.ui.ViewModel.Tiket.InsertTiketViewModel
import com.example.uasbioskop.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiTiketEntry  : DestinasiNavigasi {
    override val route = "tiket_entry"
    override val titleRes = "Isi Data Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTiketScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiTiketEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertTiketUiState = viewModel.uiStateTiket,
            penayanganList =  viewModel.penayanganList,
            onTiketValueChange = viewModel::updateInsertTiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTiket()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertTiketUiState: InsertTiketUiState,
    penayanganList: List<Penayangan>,
    onTiketValueChange: (InsertTiketUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInput(
            insertTiketUiEvent = insertTiketUiState.insertTiketUiEvent,
            onValueChange = onTiketValueChange,
            penayanganList= penayanganList,
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
    insertTiketUiEvent: InsertTiketUiEvent,
    onValueChange: (InsertTiketUiEvent) -> Unit,
    penayanganList: List<Penayangan>,
    modifier: Modifier = Modifier,
    enabled:Boolean  = true
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Dropdown untuk memilih penayangan



        Dropdown(
            penayanganList = penayanganList,
            selectedPenayanganId = insertTiketUiEvent.idPenayangan,
            onPenayanganSelected = { selectedPenayanganId ->
                val selectedPenayangan = penayanganList.find { it.idPenayangan == selectedPenayanganId }
                val hargaTiket = selectedPenayangan?.hargaTiket ?: 0
                val totalHarga = insertTiketUiEvent.jumlahTiket * hargaTiket
                onValueChange(insertTiketUiEvent.copy(idPenayangan = selectedPenayanganId, totalHarga = totalHarga))
            }
        )


        OutlinedTextField(
            value = insertTiketUiEvent.jumlahTiket.toString(),
            onValueChange = {
                val jumlahTiket = it.toIntOrNull() ?: 0
                val selectedPenayangan = penayanganList.find { it.idPenayangan == insertTiketUiEvent.idPenayangan }
                val hargaTiket = selectedPenayangan?.hargaTiket ?: 0
                val totalHarga = jumlahTiket * hargaTiket
                onValueChange(insertTiketUiEvent.copy(jumlahTiket = jumlahTiket, totalHarga = totalHarga))
            },
            label = { Text("Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertTiketUiEvent.totalHarga.toString(),
            onValueChange = {},
            label = { Text("Total Harga") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true
        )

        DynamicStatustextFild(
            label = "Status Pembayaran",
            selectedValue = insertTiketUiEvent.statusPembayaran,
            listStatus = listOf("Lunas", "Belum Lunas"), // Daftar status pembayaran
            onValueChangedEvent = { status ->
                onValueChange(insertTiketUiEvent.copy(statusPembayaran = status))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicStatustextFild(
    label:String,
    selectedValue: String,
    listStatus: List<String>,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listStatus.forEach { status ->
                DropdownMenuItem(
                    text = { Text(text = status) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(status)
                    }
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    penayanganList: List<Penayangan>,
    selectedPenayanganId: Int?,
    onPenayanganSelected: (Int) -> Unit
) {
    val options = penayanganList.map { it.idPenayangan.toString() }

    val expanded = remember { mutableStateOf(false) }
    val currentSelection = remember {
        mutableStateOf(selectedPenayanganId?.toString() ?: options.getOrNull(0)) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            value = currentSelection.value ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Id Penayangan") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        currentSelection.value = selectionOption
                        expanded.value = false
                        onPenayanganSelected(selectionOption.toInt())
                    }
                )
            }
        }

    }
}

