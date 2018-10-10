package com.valentun.justnotes.common

import com.arellomobile.mvp.MvpPresenter
import com.valentun.justnotes.data.IRepository
import com.valentun.justnotes.data.getErrorMessage
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.terrakok.cicerone.Router

abstract class BasePresenter<V : BaseView> : MvpPresenter<V>(), KoinComponent {

    val repository : IRepository by inject()

    val router: Router by inject()

    private val defaultCatch: suspend CoroutineScope.(Throwable) -> Unit = {
        viewState.showMessage(getErrorMessage(it))
    }

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

    protected fun safeAsync(tryBlock: suspend CoroutineScope.() -> Unit) {
        safeAsync(tryBlock, defaultCatch)
    }
}