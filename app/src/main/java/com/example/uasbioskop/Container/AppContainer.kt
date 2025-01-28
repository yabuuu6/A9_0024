package com.example.uasbioskop.Container

import com.example.uasbioskop.Repository.FilmRepository
import com.example.uasbioskop.Repository.NetworkFilmRepository
import com.example.uasbioskop.Repository.NetworkPenayanganRepository
import com.example.uasbioskop.Repository.StudioRepository
import com.example.uasbioskop.Repository.NetworkStudioRepository
import com.example.uasbioskop.Repository.NetworkTiketRepository
import com.example.uasbioskop.Repository.PenayanganRepository
import com.example.uasbioskop.Repository.TiketRepository
import com.example.uasbioskop.ServiceAPI.FilmService
import com.example.uasbioskop.ServiceAPI.PenayanganService
import com.example.uasbioskop.ServiceAPI.StudioService
import com.example.uasbioskop.ServiceAPI.TiketService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val filmRepository: FilmRepository
    val studioRepository: StudioRepository
    val tiketRepository : TiketRepository
    val penayanganRepository : PenayanganRepository

}

class FilmContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:8000/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val filmService: FilmService by lazy {
        retrofit.create(FilmService::class.java)
    }
    override val filmRepository: FilmRepository by lazy {
        NetworkFilmRepository(filmService)
    }

    private val studioService: StudioService by lazy {
        retrofit.create(StudioService::class.java)
    }
    override val studioRepository: StudioRepository by lazy {
        NetworkStudioRepository(studioService)
    }

    private val tiketService: TiketService by lazy {
        retrofit.create(TiketService::class.java)
    }
    override val tiketRepository: TiketRepository by lazy {
        NetworkTiketRepository(tiketService)
    }

    private val penayanganService: PenayanganService by lazy {
        retrofit.create(PenayanganService::class.java)
    }
    override val penayanganRepository: PenayanganRepository by lazy {
        NetworkPenayanganRepository(penayanganService)
    }
}
