package com.valentun.justnotes.screens.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valentun.justnotes.R
import com.valentun.justnotes.data.pojo.Note
import com.valentun.justnotes.utils.formatDate
import kotlinx.android.synthetic.main.item_note.view.*

class MainAdapter(val data: MutableList<Note>, val eventHandler: Handler) : RecyclerView.Adapter<MainAdapter.MainHolder>() {
    interface Handler {
        fun itemClicked(position: Int)
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

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            with(itemView) {
                date.text = formatDate(note.date)
                content.text = note.content

                setOnClickListener { eventHandler.itemClicked(adapterPosition) }
            }
        }
    }
}