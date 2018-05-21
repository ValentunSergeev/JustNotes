package com.valentun.justnotes.screens.main

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.valentun.justnotes.common.BaseView
import com.valentun.justnotes.data.pojo.Note

interface MainView : BaseView {
    fun showProgress()
    fun hideProgress()

    fun notesLoaded(notes: MutableList<Note>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun removeItem(position: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun addNote(item: Note)
}