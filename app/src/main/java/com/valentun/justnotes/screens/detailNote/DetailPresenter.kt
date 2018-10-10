package com.valentun.justnotes.screens.detailNote

import com.arellomobile.mvp.InjectViewState
import com.valentun.justnotes.App
import com.valentun.justnotes.R
import com.valentun.justnotes.common.BasePresenter
import com.valentun.justnotes.data.pojo.Note

@InjectViewState
class DetailPresenter(private val id: Long) : BasePresenter<DetailView>() {
    private lateinit var note: Note

    override fun onFirstViewAttach() {
        loadNote()
    }

    private fun loadNote() {
        safeAsync({
            note = repository.getNote(id).await()

            viewState.showData(note)
        })
    }

    fun editModeRequested(clickedCharacterNumber: Int) {
        viewState.enableEditMode(clickedCharacterNumber)
    }

    fun saveClicked(content: String) {
        note.content = content

        safeAsync {
            repository.updateNote(note).await()

            viewState.showMessage(R.string.update_success)
            viewState.showData(note)
        }
    }

    fun editCancelled() {
        viewState.showData(note)
    }
}