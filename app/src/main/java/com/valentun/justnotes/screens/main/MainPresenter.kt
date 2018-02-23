package com.valentun.justnotes.screens.main

import com.arellomobile.mvp.InjectViewState
import com.valentun.justnotes.App
import com.valentun.justnotes.R
import com.valentun.justnotes.common.BasePresenter
import com.valentun.justnotes.data.IRepository
import com.valentun.justnotes.data.getErrorMessage
import com.valentun.justnotes.data.pojo.Note
import javax.inject.Inject

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {
    private lateinit var notes: MutableList<Note>

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

            notes = repository.loadNotes().await().toMutableList()

            viewState.notesLoaded(notes)
        }, {
            viewState.showMessage(getErrorMessage(it))
            viewState.hideProgress()
        })
    }

    fun deleteNote(position: Int) {
        safeAsync({
            val item = notes.removeAt(position)

            repository.deleteNote(item).await()

            viewState.removeItem(position)
            viewState.showMessage(App.INSTANCE.getString(R.string.note_deleted))
        })
    }

    fun itemClicked(position: Int) {
        viewState.openDetail(notes[position].id)
    }
}
