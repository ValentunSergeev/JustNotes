package com.valentun.justnotes.common

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import ru.terrakok.cicerone.android.SupportAppNavigator

class SimpleNavigator(activity: FragmentActivity) : SupportAppNavigator(activity, 0){
    override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?) = null

    override fun createFragment(screenKey: String?, data: Any?): Fragment = Fragment()
}