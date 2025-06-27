package org.cloud99p.maroon

import org.cloud99p.maroon.data.local.DatabaseFactory
import org.cloud99p.maroon.data.local.createDataStore
import org.cloud99p.maroon.data.local.createDatabase
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(
                appViewModelModule,
                dataStoreModule,
                dataBaseModule
            )
        }
    }
}

actual val appViewModelModule = module {
    viewModelOf(::AppViewModel)
}

actual val dataStoreModule = module {
    single { createDataStore() }
}

actual val dataBaseModule = module {
    single {
        val dbBuilder = DatabaseFactory().createDatabaseBuilder()
        createDatabase(dbBuilder)
    }
}
