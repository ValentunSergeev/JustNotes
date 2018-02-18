package com.valentun.justnotes.di.modules

import com.valentun.justnotes.data.IRepository
import com.valentun.justnotes.data.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class DataModule {
    @Singleton
    @Provides
    fun provideRepository(): IRepository = Repository()
}