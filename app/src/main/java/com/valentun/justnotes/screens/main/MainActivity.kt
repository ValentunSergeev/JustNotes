package com.valentun.justnotes.screens.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.helper.ItemTouchHelper
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
    override fun initDagger() {
        App.INSTANCE.component.inject(this)
    }

    override fun provideNavigator() = MainNavigator(this)

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

        fab.setOnClickListener { presenter.newNoteClicked() }
    }

    override fun addNote(item: Note) {
        adapter.addItem(item)
    }

    override fun removeItem(position: Int) {
        adapter.removeItem(position)
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

    override fun itemClicked(position: Int) {
        presenter.itemClicked(position)
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
