package com.example.uasbioskop.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tiket(
    @SerialName("id_tiket")
    val idTiket: Int,
    @SerialName("id_penayangan")
    val idPenayangan: Int,
    @SerialName("jumlah_tiket")
    val jumlahTiket: Int,
    @SerialName("total_harga")
    val totalHarga: Int,
    @SerialName("status_pembayaran")
    val statusPembayaran: String,
    @SerialName("harga_tiket")
    val hargaTiket: Int? = null,
    @SerialName("judul_film")
    val judulFilm :String? = null,
)


@Serializable
data class AllTiketResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tiket>
)

@Serializable
data class TiketDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Tiket
)
