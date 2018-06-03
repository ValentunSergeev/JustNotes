package com.valentun.justnotes.screens.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.ViewCommand
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy

class MainStrategy : AddToEndSingleStrategy() {
    override fun <View : MvpView?> beforeApply(currentState: MutableList<ViewCommand<View>>, incomingCommand: ViewCommand<View>) {
        super.beforeApply(currentState, incomingCommand)

        if (incomingCommand is `MainView$$State`.FinishActionModeCommand) {
            currentState.removeAll {
                it is `MainView$$State`.ToggleItemSelectionCommand
                        || it is `MainView$$State`.EnableChoiceStateCommand
                        || it is `MainView$$State`.ToggleAllCommand
            }
        }
    }

    override fun <View : MvpView?> afterApply(currentState: MutableList<ViewCommand<View>>, incomingCommand: ViewCommand<View>) {
        super.afterApply(currentState, incomingCommand)

        if (incomingCommand is `MainView$$State`.FinishActionModeCommand) {
            currentState.remove(incomingCommand)
        }
    }
}