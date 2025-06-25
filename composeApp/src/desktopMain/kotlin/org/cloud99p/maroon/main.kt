package org.cloud99p.maroon

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.cloud99p.maroon.data.local.DATASTORE_FILENAME
import org.cloud99p.maroon.data.local.DatabaseFactory
import org.cloud99p.maroon.data.local.createDataStore

fun main() = application {
    val preferences = createDataStore { DATASTORE_FILENAME }
    val dbBuilder = DatabaseFactory().createDatabaseBuilder()
    ViewModel.initial(dbBuilder, preferences)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Maroon"
    ) {
        App()
    }
}
