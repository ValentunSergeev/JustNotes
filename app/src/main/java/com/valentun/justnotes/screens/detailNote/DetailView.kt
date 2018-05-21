package com.valentun.justnotes.screens.detailNote

import com.valentun.justnotes.common.BaseView
import com.valentun.justnotes.data.pojo.Note

interface DetailView : BaseView {
    fun showData(note: Note)
    fun enableEditMode(clickedCharacterNumber: Int)
}