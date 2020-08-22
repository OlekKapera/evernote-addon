package com.aleksanderkapera.evernoteaddon

import android.app.Application

class App: Application() {

    //providing context for whole app
    companion object {
        lateinit var context: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        context = this
    }
}