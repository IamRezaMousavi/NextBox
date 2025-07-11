package org.cloud99p.nextbox

import org.cloud99p.nextbox.data.local.DATASTORE_FILENAME
import org.cloud99p.nextbox.data.local.DatabaseFactory
import org.cloud99p.nextbox.data.local.createDataStore
import org.cloud99p.nextbox.data.local.createDatabase
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
