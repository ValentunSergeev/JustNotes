package com.valentun.justnotes.screens.main

import android.support.v7.util.DiffUtil
import com.valentun.justnotes.data.pojo.Note

class NoteDiffCallback(private val oldData: List<Note>,
                       private val newData: List<Note>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    override fun getOldListSize() = oldData.size

    override fun getNewListSize() = newData.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }
}