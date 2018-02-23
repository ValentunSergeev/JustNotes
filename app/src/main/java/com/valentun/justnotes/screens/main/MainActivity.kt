package com.valentun.justnotes.screens.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View.GONE
import android.view.View.VISIBLE
import com.arellomobile.mvp.presenter.InjectPresenter
import com.valentun.justnotes.R
import com.valentun.justnotes.common.BaseActivity
import com.valentun.justnotes.data.pojo.Note
import com.valentun.justnotes.extensions.setSwipeCallback
import com.valentun.justnotes.screens.detailNote.DetailActivity
import com.valentun.justnotes.screens.detailNote.EXTRA_ID
import com.valentun.justnotes.screens.newNote.NewNoteActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

private const val CREATE_REQUEST_CODE = 13

class MainActivity : BaseActivity(), MainView, MainAdapter.Handler {
    private lateinit var adapter: MainAdapter

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.setHasFixedSize(true)
        list.setSwipeCallback(ItemTouchHelper.RIGHT, { _, position ->
            presenter.deleteNote(position)
        })

        fab.setOnClickListener {
            startActivityForResult<NewNoteActivity>(CREATE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CREATE_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK)
                    presenter.loadNotes()
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun removeItem(position: Int) {
        adapter.notifyItemRemoved(position)
    }

    override fun hideProgress() {
        progress.visibility = GONE
    }

    override fun showProgress() {
        progress.visibility = VISIBLE
        list.visibility = GONE
    }

    override fun notesLoaded(notes: MutableList<Note>) {
        adapter =  MainAdapter(notes, this)
        list.adapter = adapter

        progress.visibility = GONE
        list.visibility = VISIBLE
    }

    override fun openDetail(id: Long) {
        startActivity<DetailActivity>(EXTRA_ID to id)
    }

    override fun itemClicked(position: Int) {
        presenter.itemClicked(position)
    }
}
