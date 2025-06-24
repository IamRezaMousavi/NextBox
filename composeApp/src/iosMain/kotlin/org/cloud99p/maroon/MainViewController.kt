package org.cloud99p.maroon

import androidx.compose.ui.window.ComposeUIViewController
import org.cloud99p.maroon.data.local.DatabaseFactory
import org.cloud99p.maroon.data.local.createDataStore

fun MainViewController() = ComposeUIViewController {
    val dbBuilder = DatabaseFactory().createDatabaseBuilder()
    val preferences = createDataStore()
    ViewModel.initial(dbBuilder, preferences)

    App()
}