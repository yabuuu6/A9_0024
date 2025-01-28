package com.example.uasbioskop.Repository

import com.example.uasbioskop.ServiceAPI.StudioService
import com.example.uasbioskop.model.AllStudioResponse
import com.example.uasbioskop.model.Studio
import java.io.IOException


interface StudioRepository {

    suspend fun insertStudio(studio: Studio)

    suspend fun getAllStudio(): AllStudioResponse

    suspend fun updateStudio(idStudio: Int, studio: Studio)

    suspend fun deleteStudio(idStudio: Int)

    suspend fun getStudioById(idStudio: Int): Studio

    suspend fun getStudioOption(): List<String>
}

class NetworkStudioRepository(
    private val studioApiService: StudioService
) : StudioRepository {
    override suspend fun insertStudio(studio: Studio) {
        studioApiService.insertStudio(studio)
    }

    override suspend fun getAllStudio(): AllStudioResponse =
        studioApiService.getAllStudio()

    override suspend fun updateStudio(idStudio: Int, studio: Studio) {
        studioApiService.updateStudio(idStudio, studio)
    }

    override suspend fun deleteStudio(idStudio: Int) {
        try {
            val response = studioApiService.deleteStudio(idStudio)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete studio. HTTP Status code:" +
                            "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getStudioById(idStudio: Int): Studio =
        studioApiService.getStudioById(idStudio).data

    override suspend fun getStudioOption(): List<String> {
        val studiosResponse = studioApiService.getAllStudio()
        return studiosResponse.data.map { "${it.idStudio} - ${it.namaStudio}" }
    }
}
