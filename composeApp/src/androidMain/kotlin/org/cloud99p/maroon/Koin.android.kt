package org.cloud99p.maroon

import android.content.Context
import org.cloud99p.maroon.data.local.DatabaseFactory
import org.cloud99p.maroon.data.local.createDataStore
import org.cloud99p.maroon.data.local.createDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual class KoinInitializer(private val context: Context) {
    actual fun init() {
        startKoin {
            androidContext(context)
            androidLogger()
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
    single { createDataStore(androidContext()) }
}

actual val dataBaseModule = module {
    single {
        val dbBuilder = DatabaseFactory(androidContext()).createDatabaseBuilder()
        createDatabase(dbBuilder)
    }
}
