package com.valentun.justnotes.common

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>() {
    protected fun safeAsync(tryBlock: suspend CoroutineScope.() -> Unit,
                          catchBlock: suspend CoroutineScope.(Throwable) -> Unit) {
        launch(UI) {
            try {
                tryBlock()
            } catch (e: Throwable) {
                catchBlock(e)
            }
        }
    }
}