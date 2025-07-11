package org.cloud99p.nextbox.preferences

import org.cloud99p.nextbox.data.local.DataStoreHolder
import org.cloud99p.nextbox.data.model.Theme

object DataPreferences : DataStoreHolder() {
    val counterProperty = int("counter", 0)
    var counter by counterProperty

    val themeProperty = enum("is_dark", Theme.SYSTEM)
    var theme by themeProperty

    val defaultAccountProperty = string("default_account", "Bank")
    var defaultAccount by defaultAccountProperty
}
