package com.valentun.justnotes.data

import com.valentun.justnotes.data.pojo.Note
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay

class Repository : IRepository {
    override suspend fun loadNotes(): Deferred<List<Note>> = async {
        delay(2000)
        listOf(Note("Test", 1), Note("Test 2", 2), Note("Test3", 3))
    }
}