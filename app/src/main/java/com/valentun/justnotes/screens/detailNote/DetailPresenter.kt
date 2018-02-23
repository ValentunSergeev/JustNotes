package com.valentun.justnotes.screens.detailNote

import com.arellomobile.mvp.InjectViewState
import com.valentun.justnotes.App
import com.valentun.justnotes.R
import com.valentun.justnotes.common.BasePresenter
import com.valentun.justnotes.data.IRepository
import com.valentun.justnotes.data.pojo.Note
import javax.inject.Inject

@InjectViewState
class DetailPresenter(private val id: Long) : BasePresenter<DetailView>() {
    @Inject
    lateinit var repository: IRepository

    private lateinit var note: Note

    init {
        App.INSTANCE.component.inject(this)
    }

    override fun onFirstViewAttach() {
        loadNote()
    }

    private fun loadNote() {
        safeAsync({
            note = repository.getNote(id).await()

            viewState.showData(note)
        })
    }

    fun editClicked() {
        viewState.enableEditMode()
    }

    fun saveClicked(content: String) {
        note.content = content

        safeAsync({
            repository.updateNote(note).await()

            viewState.showMessage(R.string.update_success)
            viewState.showData(note)
        })
    }
}