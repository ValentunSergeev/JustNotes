package com.valentun.justnotes.data.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.valentun.justnotes.data.pojo.Note

@Dao
interface NoteDao {
    @Query("select * from note order by id desc")
    fun getAllNotes(): List<Note>

    @Query("select * from note where id = :id")
    fun getNote(id: Long): Note

    @Insert(onConflict = REPLACE)
    fun insertNote(note: Note): Long

    @Update(onConflict = REPLACE)
    fun updateNote(note: Note)

    @Delete
    fun deleteTask(note: Note)
}