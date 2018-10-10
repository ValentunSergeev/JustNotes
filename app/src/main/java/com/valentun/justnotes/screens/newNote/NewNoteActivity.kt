package com.valentun.justnotes.screens.newNote

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.valentun.justnotes.App
import com.valentun.justnotes.R
import com.valentun.justnotes.common.BaseActivity
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : BaseActivity(), NewNoteView {
    @InjectPresenter
    lateinit var presenter: NewNotePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_save -> {
                    presenter.saveClicked(noteContent.text.toString())
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onStart() {
        super.onStart()

        setTitle(R.string.new_note_title)
    }
}
