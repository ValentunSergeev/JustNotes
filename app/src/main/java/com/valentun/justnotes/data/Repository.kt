package com.valentun.justnotes.data

import com.valentun.justnotes.data.db.NoteDao
import com.valentun.justnotes.data.pojo.Note
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

class Repository(private val noteDao: NoteDao) : IRepository {
    override suspend fun updateNote(note: Note) = async {
        noteDao.updateNote(note)
    }

    override suspend fun getNote(id: Long) = async {
        noteDao.getNote(id)
    }

    override suspend fun deleteNotes(vararg notes: Note) = async {
        noteDao.deleteNotes(*notes)
    }

    override suspend fun saveNote(content: String) = async {
        val note = Note(content, System.currentTimeMillis())
        noteDao.insertNote(note)
    }

    override suspend fun loadNotes(): Deferred<List<Note>> = async {
        noteDao.getAllNotes()
    }
}