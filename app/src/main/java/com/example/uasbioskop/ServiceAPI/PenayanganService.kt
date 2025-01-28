package com.example.uasbioskop.ServiceAPI

import com.example.uasbioskop.model.AllPenayanganResponse
import com.example.uasbioskop.model.Penayangan
import com.example.uasbioskop.model.PenayanganDetailResponse

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenayanganService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("penayangan/store")
    suspend fun insertPenayangan(@Body penayangan: Penayangan)

    @GET("penayangan/")
    suspend fun getAllPenayangan(): AllPenayanganResponse

    @GET("penayangan/{id_penayangan}")
    suspend fun getPenayanganById(@Path("id_penayangan") idPenayangan: Int): PenayanganDetailResponse

    @PUT("penayangan/{id_penayangan}")
    suspend fun updatePenayangan(@Path("id_penayangan") idPenayangan: Int, @Body penayangan: Penayangan)

    @DELETE("penayangan/{id_penayangan}")
    suspend fun deletePenayangan(@Path("id_penayangan") idPenayangan: Int): retrofit2.Response<Void>

}
