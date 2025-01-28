package com.example.uasbioskop.ui.View.Tiket

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasbioskop.ui.CostumWidget.TopAppBar
import com.example.uasbioskop.ui.Navigation.DestinasiNavigasi
import com.example.uasbioskop.ui.ViewModel.Home.UpdateViewModel
import com.example.uasbioskop.ui.ViewModel.Tiket.UpdateTiketViewModel
import com.example.uasbioskop.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiTiketUpdate : DestinasiNavigasi {
    override val route = "update_tiket"
    override val titleRes = "Update Tiket"
    const val ID_TIKET = "id_tiket"
    val routesWithArg = "$route/{$ID_TIKET}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiketUpdateScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()



    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiTiketUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            insertTiketUiState = viewModel.updateTiketUiState,
            penayanganList =  viewModel.penayanganList,
            onTiketValueChange = viewModel::updateInsertTiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTiket()
                    onNavigate()
                }
            }
        )
    }
}
