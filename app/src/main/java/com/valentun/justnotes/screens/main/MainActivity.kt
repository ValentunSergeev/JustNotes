package com.valentun.justnotes.screens.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.valentun.justnotes.R
import com.valentun.justnotes.data.pojo.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {
    override fun hideProgress() {
        progress.visibility = GONE
    }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun showError(message: String) {
        Snackbar.make(container, message, Toast.LENGTH_SHORT)
                .setAction(R.string.retry, {
                    presenter.loadNotes()
                })
                .show()
    }

    override fun showProgress() {
        progress.visibility = VISIBLE
        list.visibility = GONE
    }

    override fun notesLoaded(notes: List<Note>) {
        list.adapter = MainAdapter(notes)

        progress.visibility = GONE
        list.visibility = VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.setHasFixedSize(true)
    }
}
