package com.example.uasbioskop.ServiceAPI

import com.example.uasbioskop.model.AllStudioResponse
import com.example.uasbioskop.model.Studio
import com.example.uasbioskop.model.StudioDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudioService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("studio/store")
    suspend fun insertStudio(@Body studio: Studio)

    @GET("studio/")
    suspend fun getAllStudio(): AllStudioResponse

    @GET("studio/{id_studio}")
    suspend fun getStudioById(@Path("id_studio") idStudio: Int): StudioDetailResponse

    @PUT("studio/{id_studio}")
    suspend fun updateStudio(@Path("id_studio") idStudio: Int, @Body studio: Studio)

    @DELETE("studio/{id_studio}")
    suspend fun deleteStudio(@Path("id_studio") idStudio: Int): retrofit2.Response<Void>
}
