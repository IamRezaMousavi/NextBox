package org.cloud99p.maroon

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.cloud99p.maroon.data.local.DATA_STORE_FILE_NAME
import org.cloud99p.maroon.data.local.createDataStore

fun main() = application {
    val prefs = createDataStore { DATA_STORE_FILE_NAME }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Maroon",
    ) {
        App(prefs = prefs)
    }
}