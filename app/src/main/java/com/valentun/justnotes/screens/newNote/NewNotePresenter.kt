package com.valentun.justnotes.screens.newNote

import com.arellomobile.mvp.InjectViewState
import com.valentun.justnotes.App
import com.valentun.justnotes.R
import com.valentun.justnotes.common.BasePresenter
import com.valentun.justnotes.data.IRepository
import javax.inject.Inject

const val NOTE_CREATED_CODE = 1

@InjectViewState
class NewNotePresenter : BasePresenter<NewNoteView>() {
    init {
        App.INSTANCE.component.inject(this)
    }

    fun saveClicked(string: String) {
        safeAsync {
            if (string.isEmpty()) {
                viewState.showMessage(R.string.empty_content)
                return@safeAsync
            }

            val id = repository.saveNote(string).await()

            router.exitWithResult(NOTE_CREATED_CODE, id)
        }
    }
}