package com.example.uasbioskop.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Studio(
    @SerialName("id_studio")
    val idStudio: Int,
    @SerialName("nama_studio")
    val namaStudio: String,
    val kapasitas: Int
)


@Serializable
data class AllStudioResponse(
    val status: Boolean,
    val message: String,
    val data: List<Studio>
)

@Serializable
data class StudioDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Studio
)

