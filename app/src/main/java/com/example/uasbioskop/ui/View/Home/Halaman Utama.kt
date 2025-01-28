package com.example.uasbioskop.ui.View.Home

import android.content.res.Resources
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import com.example.uasbioskop.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import com.example.uasbioskop.ui.Navigation.DestinasiNavigasi
import kotlinx.coroutines.delay


object DestinasiHalamanUtama : DestinasiNavigasi {
    override val route = "halamanutama"
    override val titleRes = "halaman utama"
}

@Composable
fun HalamanUtamaScreen(
    navigateToSeePenayangan: () -> Unit,
    navigateToSeeStudio:() -> Unit,
    navigateToSeeHome: () -> Unit,
    navigateToSeeTiket: () -> Unit
) {

    Scaffold(
        topBar = {
            CustomTopNavbar(
                title = "",
                onMenuClick = { /* TODO: Handle menu click */ },
                onSearchSubmit = { searchQuery ->
                    println("Searching for: $searchQuery")
                }
            )
        }
        ,
        bottomBar = {
            CustomBottomBar(
                navigateToSeePenayangan = navigateToSeePenayangan,
                navigateToSeeStudio = navigateToSeeStudio,
                navigateToSeeTiket = navigateToSeeTiket,
                navigateToSeeHome = navigateToSeeHome
            )
        }
    ) {inner->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF171717), // Warna awal gradien
                            Color(0xFF171717)  // Warna akhir gradien
                        )
                    )
                )
        ) {

            // Content di bawah navbar
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)

            ) {

                //Banner Section
                Banner()
                GenreSection()
                Slogan()
                MenuSection()


            }
        }
    }

}

@Composable
fun CustomTopNavbar(
    title: String,
    onMenuClick: () -> Unit,
    onSearchSubmit: (String) -> Unit
) {
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
            .background(Color(0xFF2B2B2B)) // Warna Navbar
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .width(200.dp)
                .clickable { onMenuClick() },
            contentAlignment = Alignment.Center
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = "Selamat datang",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Yabuuu",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

        }

        Spacer(modifier = Modifier.weight(1f)) // Spacer untuk mendorong elemen berikutnya ke kanan


        Spacer(modifier = Modifier.width(8.dp))

        // Search Icon
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onSearchSubmit(searchQuery.value.text) },
            contentAlignment = Alignment.Center
        ) {

        }
    }
}

@Composable
fun Banner() {
    // Daftar ID gambar dari drawable
    val bannerImages = listOf(
        R.drawable.arriety,
        R.drawable.dora,
        R.drawable.endgame,
        R.drawable.frieren,
        R.drawable.installer
    )

    // State untuk index gambar yang sedang ditampilkan
    var currentIndex by remember { mutableStateOf(0) }

    // Coroutine untuk mengganti gambar setiap beberapa detik
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // Ganti gambar setiap 3 detik
            currentIndex = (currentIndex + 1) % bannerImages.size // Kembali ke awal setelah gambar terakhir
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp)
            .height(240.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(bannerImages.size) { index ->
            Box(
                modifier = Modifier
                    .width(340.dp)
                    .height(240.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFBB86FC))
                    .clickable { println("Clicked on banner item $index") }
            ) {
                Image(
                    painter = painterResource(id = bannerImages[(currentIndex + index) % bannerImages.size]), // Gambar yang ditampilkan
                    contentDescription = "Banner Image $index",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp)), // Mengikuti bentuk Box
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun GenreSection() {
    val genres = listOf("Action", "Comedy", "Drama", "Horror") // Genre yang akan ditampilkan
    val genreGradients = listOf(
        Brush.linearGradient( // Gradien untuk Action
            colors = listOf(
                Color(0xFFBB86FC), // Warna utama
                Color(0xFFE040FB), // Warna tambahan 1
                Color(0xFFB388FF)  // Warna tambahan 2
            )
        ),
        Brush.linearGradient( // Gradien untuk Comedy
            colors = listOf(
                Color(0xFFBB86FC), // Warna utama
                Color(0xFFE040FB), // Warna tambahan 1
                Color(0xFFB388FF)
            )
        ),
        Brush.linearGradient( // Gradien untuk Drama
            colors = listOf(
                Color(0xFFBB86FC), // Warna utama
                Color(0xFFE040FB), // Warna tambahan 1
                Color(0xFFB388FF)
            )
        ),
        Brush.linearGradient( // Gradien untuk Horror
            colors = listOf(
                Color(0xFFFF5722),
                Color(0xFFFF7043),
                Color(0xFFFF8A65)
            )
        )
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        genres.forEachIndexed { index, genre -> // Menggunakan forEachIndexed untuk mengakses indeks
            Box(
                modifier = Modifier
                    .size(100.dp, 40.dp)
                    .clip(CircleShape)
                    .background(genreGradients[index]) // Menggunakan gradien berdasarkan indeks
            ) {
                Text(
                    text = genre,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun Slogan() {
    Card(
        modifier = Modifier
            .padding(16.dp) // Padding for space between card and other elements
            .fillMaxWidth() // Fill maximum width
            .shadow(
                elevation = 8.dp, // Elevation for shadow
                shape = RoundedCornerShape(16.dp),
                clip = false
            ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFBB86FC), // Main color
                            Color(0xFF7E30E1), // Additional color 1
                            Color(0xFFB388FF)  // Additional color 2
                        )
                    )
                )

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Padding inside the card
                horizontalAlignment = Alignment.CenterHorizontally // Align content to center
            ) {
                Text(
                    text = "Slogan Baris Pertama",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White // Change text color to white
                    ),
                    textAlign = TextAlign.Center // Center align the text
                )
                Spacer(modifier = Modifier.height(8.dp)) // Space between lines
                Text(
                    text = "Slogan Baris Kedua",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White // Change text color to white
                    ),
                    textAlign = TextAlign.Center // Center align the text
                )
                Spacer(modifier = Modifier.height(8.dp)) // Space between lines
                Text(
                    text = "Slogan Baris Ketiga",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White // Change text color to white
                    ),
                    textAlign = TextAlign.Center // Center align the text
                )
            }
        }
    }
}
@Composable
fun MenuSection(


) {
    Column (modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Menu Utama",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier

            .fillMaxWidth()
        ,
        contentPadding = PaddingValues(8.dp)
    ) {
        item {
            GradientCard(
                title = "film",
                iconResId = R.drawable.clapperboard, // Ganti dengan ID drawable Anda
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFB771E5),
                        Color(0xFF8B5DFF)
                    )
                ),

                )
        }
        item {
            GradientCard(
                title = "studio",
                iconResId = R.drawable.bandoor, // Ganti dengan ID drawable Anda
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFB771E5),
                        Color(0xFF8B5DFF)
                    )
                ),

                )
        }
        item {
            GradientCard(
                title = "tiket",
                iconResId = R.drawable.ticket, // Ganti dengan ID drawable Anda
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFB771E5),
                        Color(0xFF8B5DFF)
                    )
                ),

                )
        }
        item {
            GradientCard(
                title = "penayangan",
                iconResId = R.drawable.schedule, // Ganti dengan ID drawable Anda
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFB771E5),
                        Color(0xFF8B5DFF)
                    )
                ),

                )
        }

    }
}


@Composable
fun GradientCard(
    title: String,
    iconResId: Int, // Parameter untuk ID drawable
    gradient: Brush,
    modifier: Modifier = Modifier
) {
    var clicked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.98f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                clicked = !clicked

            }
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .height(60.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = iconResId), // Memuat ikon dari drawable
                    contentDescription = title,
                    modifier = Modifier.size(50.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
fun CustomBottomBar(
    navigateToSeePenayangan: () -> Unit,
    navigateToSeeStudio: () -> Unit,
    navigateToSeeTiket: () -> Unit,
    navigateToSeeHome: () -> Unit,
) {
    // BottomAppBar tetap dipertahankan
    BottomAppBar(
        containerColor = Color(0xFF8D61D9),
        contentColor = Color.White,
        tonalElevation = 9.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp), // Padding atas dan bawah untuk membuat ruang
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tombol-tombol di dalam BottomAppBar

            IconButtonWithRoundedBackground(
                iconRes = R.drawable.baseline_home_24,
                onClick = navigateToSeeHome
            )
            IconButtonWithRoundedBackground(
                iconRes = R.drawable.baseline_movie_filter_24,
                onClick = navigateToSeePenayangan
            )
            IconButtonWithRoundedBackground(
                iconRes = R.drawable.baseline_camera_indoor_24,
                onClick = navigateToSeeStudio
            )
            IconButtonWithRoundedBackground(
                iconRes = R.drawable.baseline_local_movies_24,
                onClick = navigateToSeeTiket
            )
        }
    }
}

@Composable
fun IconButtonWithRoundedBackground(
    iconRes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(65.dp) // Ukuran kotak ikon
            .clip(RoundedCornerShape(16.dp)) // Membuat sudut membulat
            .background(
                color = Color(0xFF8D61D9),
            )
            .clickable(onClick = onClick), // Aksi klik tombol
        contentAlignment = Alignment.Center // Menjaga ikon berada di tengah
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(36.dp), // Ukuran ikon besar
            tint = Color.White
        )
    }
}

