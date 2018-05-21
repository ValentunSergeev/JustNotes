package com.valentun.justnotes.di

import com.valentun.justnotes.di.modules.AppModule
import com.valentun.justnotes.di.modules.DataModule
import com.valentun.justnotes.di.modules.NavigationModule
import com.valentun.justnotes.screens.detailNote.DetailActivity
import com.valentun.justnotes.screens.detailNote.DetailPresenter
import com.valentun.justnotes.screens.main.MainActivity
import com.valentun.justnotes.screens.main.MainPresenter
import com.valentun.justnotes.screens.newNote.NewNoteActivity
import com.valentun.justnotes.screens.newNote.NewNotePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, NavigationModule::class])
interface AppComponent {
    fun inject(presenter: MainPresenter)
    fun inject(newNotePresenter: NewNotePresenter)
    fun inject(detailPresenter: DetailPresenter)

    fun inject(mainActivity: MainActivity)
    fun inject(newNoteActivity: NewNoteActivity)
    fun inject(detailActivity: DetailActivity)
}