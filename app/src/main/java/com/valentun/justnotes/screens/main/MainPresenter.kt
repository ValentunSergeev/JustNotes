package com.valentun.justnotes.screens.main

import com.arellomobile.mvp.InjectViewState
import com.valentun.justnotes.App
import com.valentun.justnotes.R
import com.valentun.justnotes.SCREEN_DETAIL
import com.valentun.justnotes.SCREEN_NEW
import com.valentun.justnotes.common.BasePresenter
import com.valentun.justnotes.data.getErrorMessage
import com.valentun.justnotes.data.pojo.Note
import com.valentun.justnotes.extensions.copy
import com.valentun.justnotes.screens.newNote.NOTE_CREATED_CODE

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {
    private lateinit var notes: MutableList<Note>
    private var currentQuery = ""

    private var isInActionMode = false

    init {
        App.INSTANCE.component.inject(this)

        router.setResultListener(NOTE_CREATED_CODE, { newNoteCreated(it as Long) })
    }

    override fun onFirstViewAttach() {
        loadNotes()
    }

    override fun attachView(view: MainView) {
        super.attachView(view)

        if (currentQuery.isNotEmpty()) viewState.showSearch(currentQuery)
    }

    override fun onDestroy() {
        super.onDestroy()

        router.removeResultListener(NOTE_CREATED_CODE)
    }

    fun deleteNote(position: Int) {
        safeAsync {
            val item = notes[position]
            repository.deleteNotes(item).await()

            notes.remove(item)
            viewState.removeItem(item)

            viewState.showMessage(R.string.note_deleted)
        }
    }

    fun itemClicked(item: Note) {
        if (isInActionMode) {
            viewState.toggleItemSelection(item)
        } else {
            router.navigateTo(SCREEN_DETAIL, item.id)
        }
    }

    fun newNoteClicked() {
        router.navigateTo(SCREEN_NEW)
    }

    fun itemLongClicked(item: Note) {
        if (isInActionMode) {
            viewState.toggleItemSelection(item)
        } else {
            viewState.enableChoiceState(item)
        }
    }

    fun queryChanged(query: String) {
        safeAsync {
            viewState.showProgress()

            currentQuery = query

            notes = repository.getNotes(query).await()

            viewState.showData(notes.copy())
        }
    }

    fun deleteNotes(checkedNotes: List<Note>) {
        safeAsync {
            notes.removeAll(checkedNotes)
            repository.deleteNotes(*checkedNotes.toTypedArray()).await()

            isInActionMode = false

            viewState.removeItems(checkedNotes)
            viewState.finishActionMode()
        }
    }

    fun onExitActionMode() {
        isInActionMode = false
        viewState.finishActionMode()
    }

    fun actionModeShown() {
        isInActionMode = true
    }

    fun selectAllClicked() {
        viewState.toggleAll()
    }

    fun changePinState(note: Note) {
        safeAsync {
            note.isPinned = !note.isPinned

            repository.updateNote(note).await()
            notes = repository.getNotes(currentQuery).await()

            viewState.showData(notes.copy())
            viewState.finishActionMode()
        }
    }

    private fun loadNotes() {
        safeAsync({
            viewState.showProgress()

            notes = repository.getNotes(currentQuery).await()

            viewState.showData(notes.copy())
        }, {
            viewState.showMessage(getErrorMessage(it))
            viewState.hideProgress()
        })
    }

    private fun newNoteCreated(id: Long) {
        safeAsync {
            val item = repository.getNote(id).await()
            notes = repository.getNotes(currentQuery).await()

            viewState.addNote(notes.indexOf(item), item)
            viewState.showMessage(R.string.note_added)
        }

    }
}
