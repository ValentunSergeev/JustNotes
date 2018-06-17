package com.valentun.justnotes.screens.main

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.valentun.justnotes.common.BaseView
import com.valentun.justnotes.data.pojo.Note

@StateStrategyType(MainStrategy::class)
interface MainView : BaseView {
    fun showProgress()
    fun hideProgress()

    fun showData(notes: MutableList<Note>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun removeItem(item: Note)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun addNote(insertIndex: Int, item: Note)

    fun enableChoiceState(item: Note)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun removeItems(checkedNotes: List<Note>)

    @StateStrategyType(AddToEndStrategy::class)
    fun toggleItemSelection(item: Note)

    @StateStrategyType(AddToEndStrategy::class)
    fun toggleAll()

    fun finishActionMode()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSearch(currentQuery: String)
}