package com.example.uasbioskop

import android.app.Application
import com.example.uasbioskop.Container.AppContainer
import com.example.uasbioskop.Container.FilmContainer

class FilmApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = FilmContainer()
    }
}
