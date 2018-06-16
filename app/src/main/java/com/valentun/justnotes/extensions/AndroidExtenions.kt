package com.valentun.justnotes.extensions

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import java.util.logging.Handler


fun Activity.hideKeyboard() {
    val view = this.currentFocus

    if (view != null) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun RecyclerView.setSwipeCallback(directions: Int, block: (Int, Int) -> Unit) {
    val swipeToDelete = object : ItemTouchHelper.SimpleCallback(0, directions) {
        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            block(direction, viewHolder.adapterPosition)
        }
    }
    ItemTouchHelper(swipeToDelete).attachToRecyclerView(this)
}

fun View.showKeyboard() {
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.showSoftInput(this, InputMethod.SHOW_FORCED)
}