package com.valentun.justnotes.screens.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.helper.ItemTouchHelper.LEFT
import android.support.v7.widget.helper.ItemTouchHelper.RIGHT
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.arellomobile.mvp.presenter.InjectPresenter
import com.valentun.justnotes.App
import com.valentun.justnotes.R
import com.valentun.justnotes.SCREEN_DETAIL
import com.valentun.justnotes.SCREEN_NEW
import com.valentun.justnotes.common.BaseActivity
import com.valentun.justnotes.data.pojo.Note
import com.valentun.justnotes.extensions.setSwipeCallback
import com.valentun.justnotes.screens.detailNote.DetailActivity
import com.valentun.justnotes.screens.detailNote.EXTRA_ID
import com.valentun.justnotes.screens.newNote.NewNoteActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import ru.terrakok.cicerone.android.SupportAppNavigator

class MainActivity : BaseActivity(), MainView, MainAdapter.Handler {
    private val actionCallback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_delete -> presenter.deleteNotes(adapter.getCheckedNotes())
                R.id.action_select_all -> presenter.selectAllClicked()
            }

            return true
        }

        override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.main_action_menu, menu)
            presenter.actionModeShown()
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

        override fun onDestroyActionMode(mode: ActionMode?) {
            setStatusBarColor(R.color.colorPrimaryDark)
            actionMode = null
            presenter.onExitActionMode()
        }

    }

    private lateinit var adapter: MainAdapter

    private var actionMode: ActionMode? = null

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun initDagger() {
        App.INSTANCE.component.inject(this)
    }

    override fun provideNavigator() = MainNavigator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.setHasFixedSize(true)
        list.setSwipeCallback(RIGHT or LEFT, { _, position ->
            presenter.deleteNote(position)
        })

        fab.setOnClickListener { presenter.newNoteClicked() }
    }

    override fun toggleItemSelection(item: Note) {
        adapter.toggleItemCheck(item)

        updateTitleOrClearIfEmpty()
    }

    override fun toggleAll() {
        adapter.toggleAll()

        updateActionModeTitle(adapter.getCheckedNotes().size)
    }

    override fun finishActionMode() {
        actionMode?.finish()
        adapter.resetCheckedStates()
    }

    override fun removeItems(checkedNotes: List<Note>) {
        adapter.removeItems(checkedNotes)
    }

    override fun enableChoiceState(item: Note) {
        actionMode = startActionMode(actionCallback)
        setStatusBarColor(R.color.actionModeStatusBar)
        toggleItemSelection(item)
    }

    override fun itemLongClicked(item: Note) {
        presenter.itemLongClicked(item)
    }

    override fun addNote(item: Note) {
        adapter.addItem(item)
    }

    override fun removeItem(item: Note) {
        adapter.removeItem(item)

        updateTitleOrClearIfEmpty()
    }

    override fun hideProgress() {
        progress.visibility = GONE
    }

    override fun showProgress() {
        progress.visibility = VISIBLE
        list.visibility = GONE
    }

    override fun notesLoaded(notes: MutableList<Note>) {
        adapter = MainAdapter(notes, this)
        list.adapter = adapter

        progress.visibility = GONE
        list.visibility = VISIBLE
    }

    override fun itemClicked(item: Note) {
        presenter.itemClicked(item)
    }

    private fun updateTitleOrClearIfEmpty() {
        val count = adapter.getCheckedNotes().size

        if (count == 0) {
            actionMode?.finish()
        } else {
            updateActionModeTitle(count)
        }
    }

    private fun updateActionModeTitle(count: Int) {
        actionMode?.title = getString(R.string.main_action_mode_title, count)
    }

    class MainNavigator(activity: FragmentActivity) : SupportAppNavigator(activity, 0) {
        override fun createActivityIntent(context: Context, screenKey: String, data: Any?) =
                when (screenKey) {
                    SCREEN_NEW -> context.intentFor<NewNoteActivity>()
                    SCREEN_DETAIL -> context.intentFor<DetailActivity>(EXTRA_ID to data)
                    else -> null
                }

        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            TODO("Not implemented")
        }
    }
}
