package com.valentun.justnotes.data.pojo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "note")
data class Note(@ColumnInfo(name = "content") var content: String = "",
                @ColumnInfo(name = "created_at") var date: Long = 0,
                @ColumnInfo(name = "pinned") var isPinned: Boolean = false) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}