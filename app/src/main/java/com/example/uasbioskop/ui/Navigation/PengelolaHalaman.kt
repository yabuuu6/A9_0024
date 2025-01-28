package com.example.uasbioskop.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uasbioskop.ui.View.Film.*
import com.example.uasbioskop.ui.View.Home.DestinasiHalamanUtama
import com.example.uasbioskop.ui.View.Home.HalamanUtamaScreen
import com.example.uasbioskop.ui.View.Penayangan.DestinasiDetailPenayangan
import com.example.uasbioskop.ui.View.Penayangan.DestinasiPenayanganEntry
import com.example.uasbioskop.ui.View.Penayangan.DestinasiPenayanganHome
import com.example.uasbioskop.ui.View.Penayangan.DestinasiPenayanganUpdate
import com.example.uasbioskop.ui.View.Penayangan.PenayanganDetailScreen
import com.example.uasbioskop.ui.View.Penayangan.PenayanganEntryScreen
import com.example.uasbioskop.ui.View.Penayangan.PenayanganScreen
import com.example.uasbioskop.ui.View.Penayangan.PenayanganUpdateScreen
import com.example.uasbioskop.ui.View.Studio.*
import com.example.uasbioskop.ui.View.Tiket.DestinasiTiketDetail
import com.example.uasbioskop.ui.View.Tiket.DestinasiTiketEntry
import com.example.uasbioskop.ui.View.Tiket.DestinasiTiketHome
import com.example.uasbioskop.ui.View.Tiket.DestinasiTiketUpdate
import com.example.uasbioskop.ui.View.Tiket.EntryTiketScreen
import com.example.uasbioskop.ui.View.Tiket.TiketDetailScreen
import com.example.uasbioskop.ui.View.Tiket.TiketScreen
import com.example.uasbioskop.ui.View.Tiket.TiketUpdateScreen



@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHalamanUtama.route,
        modifier = Modifier,
    ) {
        //Home
        composable(DestinasiHalamanUtama.route){
            HalamanUtamaScreen(
                navigateToSeePenayangan = {navController.navigate(DestinasiPenayanganHome.route)},
                navigateToSeeStudio = {navController.navigate(DestinasiStudioHome.route)},
                navigateToSeeTiket = {navController.navigate(DestinasiTiketHome.route)},
                navigateToSeeHome = {navController.navigate(DestinasiHome.route)},
            )
        }

        // Film Routes
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { idFilm ->
                    navController.navigate("${DestinasiDetail.route}/$idFilm")
                },
                        navigateHomeBack = {navController.navigate(DestinasiHalamanUtama.route){
                    popUpTo(DestinasiHalamanUtama.route) }
                }
            )
        }
        composable(DestinasiEntry.route) {
            EntryFilmScreen(navigateBack = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetail.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.ID_FILM) {
            type = NavType.IntType
        })) {
            val idFilm = it.arguments?.getInt(DestinasiDetail.ID_FILM)
            idFilm?.let { idFilm ->
                DetailScreen(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdate.route}/$idFilm") },
                    navigateBack = { navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }}
                )
            }
        }
        composable(DestinasiUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiUpdate.ID_FILM) {
            type = NavType.IntType
        })) {
            val idFilm = it.arguments?.getInt(DestinasiUpdate.ID_FILM)
            idFilm?.let { idFilm ->
                UpdateScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }


        // Studio Routes
        composable(DestinasiStudioHome.route) {
            StudioScreen(
                navigateToStudioEntry = { navController.navigate(DestinasiStudioEntry.route) },
                onDetailClick = { idStudio ->
                    navController.navigate("${DestinasiDetailStudio.route}/$idStudio")
                },
                navigateHomeBack = {navController.navigate(DestinasiHalamanUtama.route){
                    popUpTo(DestinasiHalamanUtama.route) }
                }
            )
        }
        composable(DestinasiStudioEntry.route) {
            StudioEntryScreen(navigateBack = {
                navController.navigate(DestinasiStudioHome.route) {
                    popUpTo(DestinasiStudioHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetailStudio.routesWithArg, arguments = listOf(navArgument(DestinasiDetailStudio.ID_STUDIO) {
            type = NavType.IntType
        })) {
            val idStudio = it.arguments?.getInt(DestinasiDetailStudio.ID_STUDIO)
            idStudio?.let { idStudio ->
                StudioDetailScreen(
                    navigateToStudioUpdate = { navController.navigate("${DestinasiStudioUpdate.route}/$idStudio") },
                    navigateBack = { navController.navigate(DestinasiStudioHome.route) {
                        popUpTo(DestinasiStudioHome.route) { inclusive = true }
                    }}
                )
            }
        }
        composable(DestinasiStudioUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiStudioUpdate.ID_STUDIO) {
            type = NavType.IntType
        })) {
            val idStudio = it.arguments?.getInt(DestinasiStudioUpdate.ID_STUDIO)
            idStudio?.let { idStudio ->
                StudioUpdateScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
        //Tiket
        composable(DestinasiTiketHome.route) {
            TiketScreen(
                navigateToTiketEntry = { navController.navigate(DestinasiTiketEntry.route) },
                onDetailClick = { idTiket ->
                    navController.navigate("${DestinasiTiketDetail.route}/$idTiket")
                },
                        navigateHomeBack = {navController.navigate(DestinasiHalamanUtama.route){
                    popUpTo(DestinasiHalamanUtama.route) }
                }
            )
        }
        composable(DestinasiTiketEntry.route) {
            EntryTiketScreen(navigateBack = {
                navController.navigate(DestinasiTiketHome.route) {
                    popUpTo(DestinasiTiketHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiTiketDetail.routesWithArg, arguments = listOf(navArgument(DestinasiTiketDetail.ID_TIKET) {
            type = NavType.IntType
        })) {
            val idTiket = it.arguments?.getInt(DestinasiTiketDetail.ID_TIKET)
            idTiket?.let { idTiket ->
                TiketDetailScreen(
                    navigateToTiketUpdate = { navController.navigate("${DestinasiTiketUpdate.route}/$idTiket") },
                    navigateBack = { navController.navigate(DestinasiTiketHome.route) {
                        popUpTo(DestinasiTiketHome.route) { inclusive = true }
                    }}
                )
            }
        }
        composable(DestinasiTiketUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiTiketUpdate.ID_TIKET) {
            type = NavType.IntType
        })) {
            val idTiket = it.arguments?.getInt(DestinasiTiketUpdate.ID_TIKET)
            idTiket?.let { idTiket ->
                TiketUpdateScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
        //Penayangan
        composable(DestinasiPenayanganHome.route) {
            PenayanganScreen(
                navigateToPenayanganEntry = { navController.navigate(DestinasiPenayanganEntry.route) },
                onDetailClick = { idPenayangan ->
                    navController.navigate("${DestinasiDetailPenayangan.route}/$idPenayangan")
                },
                navigateHomeBack = {navController.navigate(DestinasiHalamanUtama.route){
                    popUpTo(DestinasiHalamanUtama.route) }
                }
            )
        }
        composable(DestinasiPenayanganEntry.route) {
            PenayanganEntryScreen(navigateBack = {
                navController.navigate(DestinasiPenayanganHome.route) {
                    popUpTo(DestinasiPenayanganHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetailPenayangan.routesWithArg, arguments = listOf(navArgument(DestinasiDetailPenayangan.ID_PENAYANGAN) {
            type = NavType.IntType
        })) {
            val idPenayangan = it.arguments?.getInt(DestinasiDetailPenayangan.ID_PENAYANGAN)
            idPenayangan?.let { idPenayangan ->
                PenayanganDetailScreen(
                    navigateToPenayanganUpdate = { navController.navigate("${DestinasiPenayanganUpdate.route}/$idPenayangan") },
                    navigateBack = { navController.navigate(DestinasiPenayanganHome.route) {
                        popUpTo(DestinasiPenayanganHome.route) { inclusive = true }
                    }}
                )
            }
        }
        composable(DestinasiPenayanganUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiPenayanganUpdate.ID_PENAYANGAN) {
            type = NavType.IntType
        })) {
            val idPenayangan = it.arguments?.getInt(DestinasiTiketUpdate.ID_TIKET)
            idPenayangan?.let { idPenayangan ->
                PenayanganUpdateScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

    }
}
