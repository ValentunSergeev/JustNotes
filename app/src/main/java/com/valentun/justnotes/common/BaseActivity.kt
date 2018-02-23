package com.valentun.justnotes.common

import android.support.design.widget.Snackbar
import com.arellomobile.mvp.MvpAppCompatActivity
import org.jetbrains.anko.contentView

abstract class BaseActivity : MvpAppCompatActivity(), BaseView {
    override fun showMessage(message: String) {
        if (contentView != null)
            Snackbar.make(contentView!!, message, Snackbar.LENGTH_SHORT)
                    .show()
    }

    override fun showMessage(messageRes: Int) {
        if (contentView != null)
            Snackbar.make(contentView!!, messageRes, Snackbar.LENGTH_SHORT)
                    .show()
    }
}