package com.valentun.justnotes.data

import com.valentun.justnotes.data.pojo.Note
import kotlinx.coroutines.experimental.Deferred

interface IRepository {
    suspend fun loadNotes() : Deferred<List<Note>>
}