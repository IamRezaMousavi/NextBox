package org.cloud99p.maroon.preferences

import org.cloud99p.maroon.data.local.DataStoreHolder
import org.cloud99p.maroon.data.model.Theme

object DataPreferences : DataStoreHolder() {
    val counterProperty = int("counter", 0)
    var counter by counterProperty

    val themeProperty = enum("is_dark", Theme.SYSTEM)
    var theme by themeProperty

    fun changeTheme() {
        theme = when (theme) {
            Theme.SYSTEM -> Theme.LIGHT
            Theme.LIGHT -> Theme.DARK
            Theme.DARK -> Theme.SYSTEM
        }
    }
}
