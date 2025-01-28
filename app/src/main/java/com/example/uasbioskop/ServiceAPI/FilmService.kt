package com.example.uasbioskop.ServiceAPI

import com.example.uasbioskop.model.AllFilmResponse
import com.example.uasbioskop.model.Film
import com.example.uasbioskop.model.FilmDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FilmService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("film/store")
    suspend fun insertFilm(@Body film: Film)

    @GET("film/")
    suspend fun getAllFilm(): AllFilmResponse

    @GET("film/{id_film}")
    suspend fun getFilmById(@Path("id_film") idFilm: Int): FilmDetailResponse

    @PUT("film/{id_film}")
    suspend fun updateFilm(@Path("id_film") idFilm: Int, @Body film: Film)

    @DELETE("film/{id_film}")
    suspend fun deleteFilm(@Path("id_film") idFilm: Int): retrofit2.Response<Void>
}

