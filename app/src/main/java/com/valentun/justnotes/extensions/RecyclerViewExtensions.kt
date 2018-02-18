package com.valentun.justnotes.extensions

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

fun RecyclerView.setSwipeCallback(directions: Int, block: (Int, Int) -> Unit) {
    val swipeToDelete = object : ItemTouchHelper.SimpleCallback(0, directions) {
        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            block(direction, viewHolder.adapterPosition)
        }
    }
    ItemTouchHelper(swipeToDelete).attachToRecyclerView(this)
}