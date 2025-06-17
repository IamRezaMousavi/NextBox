package org.cloud99p.maroon

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.cloud99p.maroon.data.local.DatabaseFactory
import org.cloud99p.maroon.data.local.createDataStore
import org.cloud99p.maroon.data.local.createDatabase

fun MainViewController() = ComposeUIViewController {
    App(
        db = remember {
            val dbBuilder = DatabaseFactory().createDatabaseBuilder()
            createDatabase(dbBuilder)
        },
        prefs = remember {
            createDataStore()
        }
    )
}