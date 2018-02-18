package com.valentun.justnotes.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.valentun.justnotes.data.pojo.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun noteDao() : NoteDao
}