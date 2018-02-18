package com.valentun.justnotes.di

import com.valentun.justnotes.di.modules.AppModule
import com.valentun.justnotes.di.modules.DataModule
import com.valentun.justnotes.screens.main.MainActivity
import com.valentun.justnotes.screens.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (DataModule::class)])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(presenter: MainPresenter)
}