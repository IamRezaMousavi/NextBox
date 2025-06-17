package org.cloud99p.maroon

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.cloud99p.maroon.data.local.DATASTORE_FILENAME
import org.cloud99p.maroon.data.local.DatabaseFactory
import org.cloud99p.maroon.data.local.createDataStore
import org.cloud99p.maroon.data.local.createDatabase

fun main() = application {
    val prefs = createDataStore { DATASTORE_FILENAME }
    val dbBuilder = DatabaseFactory().createDatabaseBuilder()
    val db = createDatabase(dbBuilder)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Maroon",
    ) {
        App(db = db, prefs = prefs)
    }
}
