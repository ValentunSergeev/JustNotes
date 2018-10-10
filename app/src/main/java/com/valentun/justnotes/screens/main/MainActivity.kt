package com.valentun.justnotes.screens.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.helper.ItemTouchHelper.LEFT
import android.support.v7.widget.helper.ItemTouchHelper.RIGHT
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.arellomobile.mvp.presenter.InjectPresenter
import com.valentun.justnotes.*
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
    private var adapter: MainAdapter? = null

    private var pendingQuery: String? = null

    private var actionMode: ActionMode? = null

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private val actionCallback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_delete -> presenter.deleteNotes(getCheckedNotes())
                R.id.action_select_all -> presenter.selectAllClicked()
                R.id.action_pin -> presenter.changePinState(getCheckedNotes().first())
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

    private val searchListener = object : SearchView.OnQueryTextListener {
        private val handler = Handler()

        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            handler.removeCallbacksAndMessages(null)

            handler.postDelayed({ presenter.queryChanged(newText) }, SEARCH_DELAY)
            return true
        }
    }

    override fun provideNavigator() = MainNavigator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.setHasFixedSize(true)
        list.setSwipeCallback(RIGHT or LEFT) { _, position ->
            presenter.deleteNote(position)
        }

        fab.setOnClickListener { presenter.newNoteClicked() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        if (pendingQuery != null) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
            searchView.clearFocus()

            pendingQuery = null
        }

        searchView.setOnQueryTextListener(searchListener)

        return true
    }

    override fun onStart() {
        super.onStart()

        setTitle(R.string.main_title)
    }

    override fun onEmptyContent() {
        showPlaceholder()
    }

    override fun showSearch(currentQuery: String) {
        pendingQuery = currentQuery
    }

    override fun toggleItemSelection(item: Note) {
        adapter?.toggleItemCheck(item)

        updateStateOrHideIfEmpty()
    }

    override fun toggleAll() {
        adapter?.toggleAll()

        updateActionModeState(getCheckedNotes().size)
    }

    override fun finishActionMode() {
        actionMode?.finish()
        adapter?.resetCheckedStates()
    }

    override fun removeItems(checkedNotes: List<Note>) {
        adapter?.removeItems(checkedNotes)
    }

    override fun enableChoiceState(item: Note) {
        actionMode = startActionMode(actionCallback)
        setStatusBarColor(R.color.actionModeStatusBar)
        toggleItemSelection(item)
    }

    override fun itemLongClicked(item: Note) {
        presenter.itemLongClicked(item)
    }

    override fun addNote(insertIndex: Int, item: Note) {
        adapter?.addItem(insertIndex, item)

        if (!list.isShown) {
            showList()
        }
    }

    override fun removeItem(item: Note) {
        adapter?.removeItem(item)

        updateStateOrHideIfEmpty()
    }

    override fun hideProgress() {
        progress.visibility = GONE
    }

    override fun showProgress() {
        progress.visibility = VISIBLE
        list.visibility = GONE
        placeholder.visibility = GONE
    }

    override fun showData(notes: MutableList<Note>) {
        adapter = MainAdapter(notes, this)
        list.adapter = adapter

        if (notes.size > 0) {
            showList()
        } else {
            showPlaceholder()
        }
    }

    override fun itemClicked(item: Note) {
        presenter.itemClicked(item)
    }

    private fun showList() {
        list.visibility = VISIBLE
        progress.visibility = GONE
        placeholder.visibility = GONE
    }

    private fun showPlaceholder() {
        list.visibility = GONE
        progress.visibility = GONE
        placeholder.visibility = VISIBLE
    }

    private fun updateStateOrHideIfEmpty() {
        val count = getCheckedNotes().size

        if (count == 0) {
            actionMode?.finish()
        } else {
            updateActionModeState(count)
        }
    }

    private fun getCheckedNotes() = adapter?.getCheckedNotes() ?: emptyList()

    private fun updateActionModeState(count: Int) {
        actionMode?.title = getString(R.string.main_action_mode_title, count)

        val pinItem = actionMode?.menu?.findItem(R.id.action_pin)

        if (count == 1) {
            pinItem?.isVisible = true

            val iconId = if (getCheckedNotes().first().isPinned) R.drawable.ic_lock_open else R.drawable.ic_lock_close
            pinItem?.setIcon(iconId)
        } else {
            pinItem?.isVisible = false
        }
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
