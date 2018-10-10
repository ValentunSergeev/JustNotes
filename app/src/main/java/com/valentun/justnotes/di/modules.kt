package com.valentun.justnotes.di

import android.arch.persistence.room.Room
import com.valentun.justnotes.DB_NAME
import com.valentun.justnotes.data.IRepository
import com.valentun.justnotes.data.Repository
import com.valentun.justnotes.data.db.Database
import org.koin.dsl.module.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val dataModule = module {
    single {
        Room.databaseBuilder(get(), Database::class.java, DB_NAME)
                .build()
    }

    single {
        get<Database>().noteDao()
    }

    single<IRepository> {
        Repository(get())
    }
}

val navigationModule = module {
    single<Cicerone<Router>> {
        Cicerone.create()
    }

    single {
        get<Cicerone<Router>>().router
    }

    single {
        get<Cicerone<Router>>().navigatorHolder
    }
}