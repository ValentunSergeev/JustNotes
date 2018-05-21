package com.valentun.justnotes.common

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.arellomobile.mvp.MvpAppCompatActivity
import org.jetbrains.anko.contentView
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity(), BaseView {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private var navigator: Navigator? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        initDagger()

        super.onCreate(savedInstanceState)

        navigator = provideNavigator()
    }

    protected abstract fun initDagger()

    protected open fun provideNavigator() : Navigator = SimpleNavigator(this)

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}