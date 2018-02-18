package com.valentun.justnotes.screens.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.valentun.justnotes.data.pojo.Note

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun showProgress()
    fun hideProgress()
    fun notesLoaded(notes: MutableList<Note>)
    fun showMessage(message: String)
    fun removeItem(position: Int)
}