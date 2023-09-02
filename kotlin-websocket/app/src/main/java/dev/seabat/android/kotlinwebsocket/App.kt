package dev.seabat.android.kotlinwebsocket

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: Context
        fun getApplicationContext(): Context {
            return instance.applicationContext
        }
    }
}
