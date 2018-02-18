package com.valentun.justnotes.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.valentun.justnotes.DB_NAME
import com.valentun.justnotes.data.IRepository
import com.valentun.justnotes.data.Repository
import com.valentun.justnotes.data.db.Database
import com.valentun.justnotes.data.db.NoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class DataModule {
    @Provides
    @Singleton
    fun provideDatabase(ctx: Context) =
            Room.databaseBuilder(ctx, Database::class.java, DB_NAME)
                    .build()

    @Provides
    @Singleton
    fun provideNoteDao(db: Database) = db.noteDao()

    @Singleton
    @Provides
    fun provideRepository(noteDao: NoteDao): IRepository = Repository(noteDao)
}