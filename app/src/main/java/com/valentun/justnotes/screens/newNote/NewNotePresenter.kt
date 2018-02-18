package com.valentun.justnotes.screens.newNote

import com.arellomobile.mvp.InjectViewState
import com.valentun.justnotes.App
import com.valentun.justnotes.R
import com.valentun.justnotes.common.BasePresenter
import com.valentun.justnotes.data.IRepository
import com.valentun.justnotes.data.getErrorMessage
import javax.inject.Inject

@InjectViewState
class NewNotePresenter : BasePresenter<NewNoteView>() {
    @Inject
    lateinit var repository: IRepository

    init {
        App.INSTANCE.component.inject(this)
    }

    fun saveClicked(string: String) {
        safeAsync({
            if (string.isEmpty()) {
                viewState.showError(App.INSTANCE.getString(R.string.empty_content))
                return@safeAsync
            }

            viewState.showProgress()

            repository.saveNote(string).await()

            viewState.noteSaved()
        }, {
            viewState.showError(getErrorMessage(it))
        })
    }
}