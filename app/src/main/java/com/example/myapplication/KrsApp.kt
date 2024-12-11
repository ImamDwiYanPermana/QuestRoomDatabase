package com.example.myapplication

import android.app.Application
import com.example.myapplication.dependenciesinjection.ContainerApp

class KrsApp: Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)
    }
}