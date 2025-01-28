package com.example.uasbioskop.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Penayangan(
    @SerialName("id_penayangan")
    val idPenayangan: Int,
    @SerialName("id_film")
    val idFilm: Int,
    @SerialName("id_studio")
    val idStudio: Int,
    @SerialName("tanggal_penayangan")
    val tanggalPenayangan: String,
    @SerialName("harga_tiket")
    val hargaTiket: Int,
    @SerialName("nama_studio")
    val namaStudio: String? = null,
    @SerialName("judul_film")
    val judulFilm :String? = null,
)

@Serializable
data class AllPenayanganResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penayangan>
)

@Serializable
data class PenayanganDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Penayangan
)






