package com.valentun.justnotes.screens.main

import com.arellomobile.mvp.InjectViewState
import com.valentun.justnotes.App
import com.valentun.justnotes.R
import com.valentun.justnotes.SCREEN_DETAIL
import com.valentun.justnotes.SCREEN_NEW
import com.valentun.justnotes.common.BasePresenter
import com.valentun.justnotes.data.getErrorMessage
import com.valentun.justnotes.data.pojo.Note
import com.valentun.justnotes.screens.newNote.NOTE_CREATED_CODE

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {
    private lateinit var notes: MutableList<Note>

    init {
        App.INSTANCE.component.inject(this)

        router.setResultListener(NOTE_CREATED_CODE, { newNoteCreated(it as Long) })
    }

    override fun onFirstViewAttach() {
        loadNotes()
    }

    override fun onDestroy() {
        super.onDestroy()

        router.removeResultListener(NOTE_CREATED_CODE)
    }

    fun deleteNote(position: Int) {
        safeAsync {
            val item = notes.removeAt(position)

            repository.deleteNote(item).await()

            viewState.removeItem(position)
            viewState.showMessage(App.INSTANCE.getString(R.string.note_deleted))
        }
    }

    fun itemClicked(position: Int) {
        router.navigateTo(SCREEN_DETAIL, notes[position].id)
    }

    fun newNoteClicked() {
        router.navigateTo(SCREEN_NEW)
    }

    private fun loadNotes() {
        safeAsync({
            viewState.showProgress()

            notes = repository.loadNotes().await().toMutableList()

            viewState.notesLoaded(notes.toMutableList())
        }, {
            viewState.showMessage(getErrorMessage(it))
            viewState.hideProgress()
        })
    }

    private fun newNoteCreated(id: Long) {
        safeAsync {
            val item = repository.getNote(id).await()

            notes.add(0, item)

            viewState.addNote(item)
            viewState.showMessage(R.string.note_added)
        }
    }
}
