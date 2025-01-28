package com.example.uasbioskop.Repository

import com.example.uasbioskop.ServiceAPI.FilmService
import com.example.uasbioskop.model.AllFilmResponse
import com.example.uasbioskop.model.Film
import java.io.IOException

interface FilmRepository {

    suspend fun insertFilm(film: Film)

    suspend fun getAllFilm() :AllFilmResponse

    suspend fun updateFilm(idFilm: Int, film: Film)

    suspend fun deleteFilm(idFilm: Int)

    suspend fun getFilmById(idFilm: Int): Film

    suspend fun getFilmOption():List<String>
}

class NetworkFilmRepository(
    private val filmApiService: FilmService
) : FilmRepository {
    override suspend fun insertFilm(film: Film) {
        filmApiService.insertFilm(film)
    }

    override suspend fun getAllFilm(): AllFilmResponse =
        filmApiService.getAllFilm()

    override suspend fun updateFilm(idFilm: Int, film: Film) {
        filmApiService.updateFilm(idFilm, film)
    }

    override suspend fun deleteFilm(idFilm: Int) {
        try {
            val response = filmApiService.deleteFilm(idFilm)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete film. HTTP Status code:" +
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

    override suspend fun getFilmById(idFilm: Int): Film =
        filmApiService.getFilmById(idFilm).data

    override suspend fun getFilmOption(): List<String> {
        val response = filmApiService.getAllFilm()
        if (!response.status) {
            throw IOException("Failed to fetch films: ${response.message}")
        }
        return response.data.map { "${it.idFilm} - ${it.judulFilm}" }
    }
}
