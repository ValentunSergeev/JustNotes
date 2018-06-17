package com.valentun.justnotes.data

import com.valentun.justnotes.data.pojo.Note
import kotlinx.coroutines.experimental.Deferred

interface IRepository {
    suspend fun saveNote(content: String) : Deferred<Long>
    suspend fun deleteNotes(vararg notes: Note) : Deferred<Unit>
    suspend fun getNote(id: Long) : Deferred<Note>
    suspend fun updateNote(note: Note) : Deferred<Unit>
    suspend fun getNotes(query: String): Deferred<MutableList<Note>>
}