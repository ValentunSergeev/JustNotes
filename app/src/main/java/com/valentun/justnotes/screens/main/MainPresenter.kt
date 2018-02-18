package com.valentun.justnotes.screens.main

import com.arellomobile.mvp.InjectViewState
import com.valentun.justnotes.App
import com.valentun.justnotes.common.BasePresenter
import com.valentun.justnotes.data.IRepository
import com.valentun.justnotes.data.getErrorMessage
import com.valentun.justnotes.data.pojo.Note
import javax.inject.Inject

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {
    private lateinit var notes: List<Note>

    @Inject
    lateinit var repository: IRepository

    init {
        App.INSTANCE.component.inject(this)
    }

    override fun onFirstViewAttach() {
        loadNotes()
    }

    fun loadNotes() {
        safeAsync({
            viewState.showProgress()

            notes = repository.loadNotes().await()

            viewState.notesLoaded(notes)
        }, { error ->
            viewState.showError(getErrorMessage(error))
            viewState.hideProgress()
        })
    }
}
