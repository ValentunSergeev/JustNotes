package com.valentun.justnotes

import android.app.Application
import com.valentun.justnotes.di.dataModule
import com.valentun.justnotes.di.navigationModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(dataModule, navigationModule))
    }
}