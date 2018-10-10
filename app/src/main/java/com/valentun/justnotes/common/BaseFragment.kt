package com.valentun.justnotes.common

import android.support.design.widget.Snackbar
import com.arellomobile.mvp.MvpAppCompatFragment

class BaseFragment : MvpAppCompatFragment(), BaseView {
    override fun showMessage(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    override fun showMessage(messageRes: Int) {
        showMessage(getString(messageRes))
    }
}