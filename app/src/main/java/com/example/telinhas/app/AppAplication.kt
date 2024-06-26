package com.example.telinhas.app

import android.app.Application
import com.example.telinhas.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppAplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@AppAplication)
            modules(appModules)
        }
    }
}