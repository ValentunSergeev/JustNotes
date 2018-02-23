package com.valentun.justnotes.screens.detailNote

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.valentun.justnotes.R
import com.valentun.justnotes.common.BaseActivity
import com.valentun.justnotes.data.pojo.Note
import com.valentun.justnotes.extensions.hideKeyboard
import kotlinx.android.synthetic.main.activity_detail.*

const val EXTRA_ID = "EXTRA_ID"
private const val DEFAULT_ID = -1L

class DetailActivity : BaseActivity(), DetailView {
    private var itemSave: MenuItem? = null
    private var itemEdit: MenuItem? = null

    private var pendingMenuAction: () -> Unit = {}

    @InjectPresenter
    lateinit var presenter: DetailPresenter

    @ProvidePresenter
    fun providePresenter() = DetailPresenter(getId())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)

        itemEdit = menu.findItem(R.id.action_edit)
        itemSave = menu.findItem(R.id.action_save)

        pendingMenuAction()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_edit -> {
                    presenter.editClicked()
                    true
                }
                R.id.action_save -> {
                    hideKeyboard()
                    val content = editContent.text.toString()
                    presenter.saveClicked(content)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun showData(note: Note) {
        switchMenuItemsState(editMode = false)

        viewContent.visibility = VISIBLE
        editContent.visibility = GONE

        viewContent.text = note.content
        editContent.setText(note.content, TextView.BufferType.EDITABLE)
    }

    override fun enableEditMode() {
        switchMenuItemsState(editMode = true)

        viewContent.visibility = GONE
        editContent.visibility = VISIBLE
    }

    private fun getId(): Long {
        return intent.getLongExtra(EXTRA_ID, DEFAULT_ID)
    }

    private fun switchMenuItemsState(editMode: Boolean) {
        if (itemEdit != null && itemSave != null) {
            itemSave!!.isVisible = editMode
            itemEdit!!.isVisible = !editMode

            pendingMenuAction = {}
        } else {
            pendingMenuAction = {
                itemSave!!.isVisible = editMode
                itemEdit!!.isVisible = !editMode
            }
        }
    }
}
