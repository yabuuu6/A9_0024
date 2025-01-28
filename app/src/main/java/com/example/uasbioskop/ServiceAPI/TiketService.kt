package com.example.uasbioskop.ServiceAPI

import com.example.uasbioskop.model.AllTiketResponse
import com.example.uasbioskop.model.Tiket
import com.example.uasbioskop.model.TiketDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface TiketService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("tiket/store")
    suspend fun insertTiket(@Body tiket: Tiket)

    @GET("tiket")
    suspend fun getAllTiket(): AllTiketResponse

    @GET("tiket/{id_tiket}")
    suspend fun getTiketById(@Path("id_tiket") idTiket: Int): TiketDetailResponse

    @PUT("tiket/{id_tiket}")
    suspend fun updateTiket(@Path("id_tiket") idTiket: Int, @Body tiket: Tiket)

    @DELETE("tiket/{id_tiket}")
    suspend fun deleteTiket(@Path("id_tiket") idTiket: Int): retrofit2.Response<Void>
}

