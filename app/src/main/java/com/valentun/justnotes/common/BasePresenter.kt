package com.valentun.justnotes.common

import com.arellomobile.mvp.MvpPresenter
import com.valentun.justnotes.data.getErrorMessage
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

abstract class BasePresenter<V : BaseView> : MvpPresenter<V>() {
    private val defaultCatch: suspend CoroutineScope.(Throwable) -> Unit = {
        viewState.showMessage(getErrorMessage(it))
    }

    protected fun safeAsync(tryBlock: suspend CoroutineScope.() -> Unit,
                            catchBlock: suspend CoroutineScope.(Throwable) -> Unit = defaultCatch) {
        launch(UI) {
            try {
                tryBlock()
            } catch (e: Throwable) {
                catchBlock(e)
            }
        }
    }
}