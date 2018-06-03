package com.valentun.justnotes.screens.main

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.*
import com.valentun.justnotes.R
import com.valentun.justnotes.data.pojo.Note
import com.valentun.justnotes.utils.formatDate
import kotlinx.android.synthetic.main.item_note.view.*

class MainAdapter(val data: MutableList<Note>, val eventHandler: Handler) : RecyclerView.Adapter<MainAdapter.MainHolder>() {
    private val checkedStates = ArrayList<Note>()

    interface Handler {

        fun itemClicked(item: Note)
        fun itemLongClicked(item: Note)
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

    fun addItem(item: Note) {
        data.add(0, item)
        notifyItemInserted(0)
    }

    fun removeItem(item: Note) {
        val position = data.indexOf(item)

        checkedStates.remove(item)
        data.remove(item)

        notifyItemRemoved(position)
    }

    fun getCheckedNotes(): List<Note> {
        return checkedStates
    }

    fun removeItems(items: List<Note>) {
        data.removeAll(items)
        notifyDataSetChanged()
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


    inner class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            with(itemView) {
                date.text = formatDate(note.date)
                content.text = note.content

                val isChecked = checkedStates.contains(note)
                val color = if (isChecked) R.color.selectedItem else R.color.white
                itemNoteContainer.setBackgroundColor(ContextCompat.getColor(context, color))

                setOnClickListener { eventHandler.itemClicked(note) }
                setOnLongClickListener {
                    eventHandler.itemLongClicked(note)
                    true
                }
            }
        }
    }
}