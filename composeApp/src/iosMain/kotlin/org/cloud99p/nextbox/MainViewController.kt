package org.cloud99p.nextbox

import androidx.compose.ui.window.ComposeUIViewController

@Suppress("ktlint:standard:function-naming")
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}
