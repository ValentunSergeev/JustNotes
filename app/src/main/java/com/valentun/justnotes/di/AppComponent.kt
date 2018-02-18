package com.valentun.justnotes.di

import com.valentun.justnotes.di.modules.AppModule
import com.valentun.justnotes.di.modules.DataModule
import com.valentun.justnotes.screens.main.MainPresenter
import com.valentun.justnotes.screens.newNote.NewNotePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (DataModule::class)])
interface AppComponent {
    fun inject(presenter: MainPresenter)
    fun inject(newNotePresenter: NewNotePresenter)
}