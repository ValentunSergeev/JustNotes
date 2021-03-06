package com.valentun.justnotes.screens.main

import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.valentun.justnotes.R
import com.valentun.justnotes.data.pojo.Note
import com.valentun.justnotes.extensions.copy
import com.valentun.justnotes.utils.formatDate
import kotlinx.android.synthetic.main.item_note.view.*

class MainAdapter(val data: MutableList<Note>, val eventHandler: Handler) : RecyclerView.Adapter<MainAdapter.MainHolder>() {
    private val checkedStates = ArrayList<Note>()

    init {
        notifyIfEmpty()
    }

    interface Handler {

        fun itemClicked(item: Note)

        fun itemLongClicked(item: Note)
        fun onEmptyContent()

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return MainHolder(view)
    }

    fun addItem(index: Int, item: Note) {
        data.add(index, item)
        notifyItemInserted(index)
    }

    fun removeItem(item: Note) {
        val position = data.indexOf(item)

        checkedStates.remove(item)
        data.remove(item)

        notifyItemRemoved(position)

        notifyIfEmpty()
    }

    fun getCheckedNotes(): List<Note> {
        return checkedStates
    }

    fun removeItems(items: List<Note>) {
        val temp = data.copy()

        data.removeAll(items)

        val callback = NoteDiffCallback(temp, data)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)

        notifyIfEmpty()
    }

    fun toggleItemCheck(item: Note) {
        if (item in checkedStates) {
            checkedStates.remove(item)
        } else {
            checkedStates.add(item)
        }
        notifyItemChanged(data.indexOf(item))
    }

    fun resetCheckedStates() {
        checkedStates.clear()
        notifyDataSetChanged()
    }

    fun toggleAll() {
        if (checkedStates.size < data.size) {
            checkedStates.clear()
            checkedStates.addAll(data)
        } else {
            checkedStates.clear()
        }

        notifyDataSetChanged()
    }

    private fun notifyIfEmpty() {
        if (data.isEmpty()) {
            eventHandler.onEmptyContent()
        }
    }


    inner class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            with(itemView) {
                date.text = formatDate(note.date)
                content.text = note.content

                val isChecked = checkedStates.contains(note)
                val color = if (isChecked) R.color.selectedItem else R.color.white
                itemNoteContainer.setBackgroundColor(ContextCompat.getColor(context, color))

                val pinIndicatorVisibility = if (note.isPinned) VISIBLE else INVISIBLE
                pinIndicator.visibility = pinIndicatorVisibility

                setOnClickListener { eventHandler.itemClicked(note) }
                setOnLongClickListener {
                    eventHandler.itemLongClicked(note)
                    true
                }
            }
        }
    }
}