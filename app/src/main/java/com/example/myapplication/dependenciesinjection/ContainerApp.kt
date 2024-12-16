package com.example.myapplication.dependenciesinjection

import android.content.Context
import com.example.myapplication.data.database.KrsDatabase
import com.example.myapplication.repository.LocalRepositoryMhs
import com.example.myapplication.repository.RepositoryMhs

interface InterdaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ContainerApp(private val context: Context) : InterdaceContainerApp{
    override val repositoryMhs: RepositoryMhs by lazy {
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}