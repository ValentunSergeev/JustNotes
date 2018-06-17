package com.valentun.justnotes.data.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.valentun.justnotes.data.pojo.Note

@Dao
interface NoteDao {
    @Query("select * from note where content like :query order by pinned desc, id desc")
    fun findNotes(query: String): List<Note>

    @Query("select * from note where id = :id")
    fun getNote(id: Long): Note

    @Insert(onConflict = REPLACE)
    fun insertNote(note: Note): Long

    @Update(onConflict = REPLACE)
    fun updateNote(note: Note)

    @Delete
    fun deleteNotes(vararg notes: Note)
}