package com.example.uasbioskop.Repository

import com.example.uasbioskop.ServiceAPI.TiketService
import com.example.uasbioskop.model.AllTiketResponse
import com.example.uasbioskop.model.Tiket
import java.io.IOException

interface TiketRepository {

    suspend fun insertTiket(tiket: Tiket)

    suspend fun getAllTiket(): AllTiketResponse

    suspend fun updateTiket(idTiket: Int, tiket: Tiket)

    suspend fun deleteTiket(idTiket: Int)

    suspend fun getTiketById(idTiket: Int): Tiket
}

class NetworkTiketRepository(
    private val tiketApiService: TiketService
) : TiketRepository {
    override suspend fun insertTiket(tiket: Tiket) {
        tiketApiService.insertTiket(tiket)
    }

    override suspend fun getAllTiket(): AllTiketResponse =
        tiketApiService.getAllTiket()

    override suspend fun updateTiket(idTiket: Int, tiket: Tiket) {
        tiketApiService.updateTiket(idTiket, tiket)
    }

    override suspend fun deleteTiket(idTiket: Int) {
        try {
            val response = tiketApiService.deleteTiket(idTiket)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete tiket. HTTP Status code:" +
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

    override suspend fun getTiketById(idTiket: Int): Tiket =
        tiketApiService.getTiketById(idTiket).data
}
