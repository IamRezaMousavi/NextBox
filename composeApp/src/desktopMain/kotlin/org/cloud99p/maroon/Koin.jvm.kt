package org.cloud99p.maroon

import org.cloud99p.maroon.data.local.DATASTORE_FILENAME
import org.cloud99p.maroon.data.local.DatabaseFactory
import org.cloud99p.maroon.data.local.createDataStore
import org.cloud99p.maroon.data.local.createDatabase
import org.koin.dsl.module

actual val dataStoreModule = module {
    single { createDataStore { DATASTORE_FILENAME } }
}

actual val dataBaseModule = module {
    single {
        val dbBuilder = DatabaseFactory().createDatabaseBuilder()
        createDatabase(dbBuilder)
    }
}
