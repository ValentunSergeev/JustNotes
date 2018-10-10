package com.valentun.justnotes.screens.detailNote

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View.*
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.valentun.justnotes.App
import com.valentun.justnotes.R
import com.valentun.justnotes.common.BaseActivity
import com.valentun.justnotes.data.pojo.Note
import com.valentun.justnotes.extensions.hideKeyboard
import com.valentun.justnotes.extensions.showKeyboard
import kotlinx.android.synthetic.main.activity_detail.*

const val EXTRA_ID = "EXTRA_ID"
private const val DEFAULT_ID = -1L

class DetailActivity : BaseActivity(), DetailView {
    private var itemSave: MenuItem? = null

    private var pendingMenuAction: () -> Unit = {}

    private var isInEditMode = false

    @InjectPresenter
    lateinit var presenter: DetailPresenter

    @ProvidePresenter
    fun providePresenter() = DetailPresenter(getId())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewContent.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val offset = viewContent.getOffsetForPosition(event.x, event.y)
                presenter.editModeRequested(offset)
            }
            false
        }

        setTitle(R.string.detail_title)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)

        itemSave = menu.findItem(R.id.action_save)

        pendingMenuAction()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_save -> {
                    hideKeyboard()
                    val content = editContent.text.toString()
                    presenter.saveClicked(content)
                    true
                }
                android.R.id.home -> {
                    hideKeyboard()
                    false
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun showData(note: Note) {
        switchMenuItemsState(editMode = false)

        setTitle(R.string.detail_title)

        viewContent.visibility = VISIBLE
        editContent.visibility = INVISIBLE

        viewContent.text = note.content
        editContent.setText(note.content, TextView.BufferType.EDITABLE)

        isInEditMode = false
    }

    override fun enableEditMode(clickedCharacterNumber: Int) {
        switchMenuItemsState(editMode = true)

        setTitle(R.string.detail_edit_title)

        viewContent.visibility = INVISIBLE
        editContent.visibility = VISIBLE

        editContent.requestFocus()
        editContent.setSelection(clickedCharacterNumber)
        editContent.showKeyboard()

        isInEditMode = true
    }

    private fun getId(): Long {
        return intent.getLongExtra(EXTRA_ID, DEFAULT_ID)
    }

    private fun switchMenuItemsState(editMode: Boolean) {
        if (itemSave != null) {
            itemSave!!.isVisible = editMode

            pendingMenuAction = {}
        } else {
            pendingMenuAction = {
                itemSave!!.isVisible = editMode
            }
        }
    }

    override fun onBackPressed() {
        if (isInEditMode) {
            presenter.editCancelled()
        } else {
            super.onBackPressed()
        }
    }
}
