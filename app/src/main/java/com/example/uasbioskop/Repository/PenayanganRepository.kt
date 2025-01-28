package com.example.uasbioskop.Repository

import com.example.uasbioskop.ServiceAPI.PenayanganService
import com.example.uasbioskop.model.AllPenayanganResponse
import com.example.uasbioskop.model.AllStudioResponse
import com.example.uasbioskop.model.Penayangan
import java.io.IOException

interface PenayanganRepository {

    suspend fun insertPenayangan(penayangan: Penayangan)

    suspend fun getAllPenayangan(): AllPenayanganResponse

    suspend fun updatePenayangan(idPenayangan: Int, penayangan: Penayangan)

    suspend fun deletePenayangan(idPenayangan: Int)

    suspend fun getPenayanganById(idPenayangan: Int): Penayangan

    suspend fun getPenayanganOption(): List<Penayangan>

}

class NetworkPenayanganRepository(
    private val penayanganApiService: PenayanganService
) : PenayanganRepository {
    override suspend fun insertPenayangan(penayangan: Penayangan) {
        penayanganApiService.insertPenayangan(penayangan)
    }

    override suspend fun getAllPenayangan(): AllPenayanganResponse =
        penayanganApiService.getAllPenayangan()

    override suspend fun updatePenayangan(idPenayangan: Int, penayangan: Penayangan) {
        penayanganApiService.updatePenayangan(idPenayangan, penayangan)
    }

    override suspend fun deletePenayangan(idPenayangan: Int) {
        try {
            val response = penayanganApiService.deletePenayangan(idPenayangan)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete penayangan. HTTP Status code:" +
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

    override suspend fun getPenayanganOption(): List<Penayangan> {
        return penayanganApiService.getAllPenayangan().data
    }

    override suspend fun getPenayanganById(idPenayangan: Int): Penayangan =
        penayanganApiService.getPenayanganById(idPenayangan).data
}
