package com.valentun.justnotes.screens.newNote

import com.arellomobile.mvp.MvpView

interface NewNoteView : MvpView {
    fun showProgress()
    fun showError(message: String)
    fun noteSaved()
}