package com.valentun.justnotes

import android.app.Application
import com.valentun.justnotes.di.AppComponent
import com.valentun.justnotes.di.DaggerAppComponent
import com.valentun.justnotes.di.modules.AppModule

class App : Application() {
    lateinit var component: AppComponent
        private set

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}