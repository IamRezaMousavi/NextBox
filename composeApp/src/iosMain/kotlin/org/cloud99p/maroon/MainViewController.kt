package org.cloud99p.maroon

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.cloud99p.maroon.data.local.createDataStore

fun MainViewController() = ComposeUIViewController {
    App(
        prefs = remember {
            createDataStore()
        }
    )
}