package com.valentun.justnotes.screens.main

import com.valentun.justnotes.common.BaseView
import com.valentun.justnotes.data.pojo.Note

interface MainView : BaseView {
    fun showProgress()
    fun hideProgress()

    fun notesLoaded(notes: MutableList<Note>)

    fun removeItem(position: Int)
    fun openDetail(id: Long)
}