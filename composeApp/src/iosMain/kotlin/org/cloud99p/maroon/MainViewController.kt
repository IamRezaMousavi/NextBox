package org.cloud99p.maroon

import androidx.compose.ui.window.ComposeUIViewController

@Suppress("ktlint:standard:function-naming")
fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) {
    App()
}
