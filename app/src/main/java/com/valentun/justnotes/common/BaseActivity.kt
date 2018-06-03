package com.valentun.justnotes.common

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatActivity
import org.jetbrains.anko.contentView
import org.jetbrains.anko.doFromSdk
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

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    @SuppressLint("NewApi")
    protected fun setStatusBarColor(colorRes: Int) {
        doFromSdk(Build.VERSION_CODES.LOLLIPOP) {
            val color = ContextCompat.getColor(this, colorRes)
            window.statusBarColor = color
        }
    }

    protected abstract fun initDagger()

    protected open fun provideNavigator() : Navigator = SimpleNavigator(this)
}