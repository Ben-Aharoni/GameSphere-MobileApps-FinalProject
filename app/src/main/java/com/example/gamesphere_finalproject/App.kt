package com.example.gamesphere_finalproject

import android.app.Application
import com.example.gamesphere_finalproject.utilities.ImageLoader

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        ImageLoader.init(this)
    }
}