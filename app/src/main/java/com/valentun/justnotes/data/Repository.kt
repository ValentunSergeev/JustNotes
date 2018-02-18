package com.valentun.justnotes.data

import com.valentun.justnotes.data.db.NoteDao
import com.valentun.justnotes.data.pojo.Note
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

class Repository(private val noteDao: NoteDao) : IRepository {
    override suspend fun deleteNote(note: Note): Deferred<Unit> = async {
        noteDao.deleteTask(note)
    }

    override suspend fun saveNote(content: String) = async {
        val note = Note(content, System.currentTimeMillis())
        noteDao.insertNote(note)
    }

    override suspend fun loadNotes(): Deferred<List<Note>> = async {
        noteDao.getAllNotes()
    }
}