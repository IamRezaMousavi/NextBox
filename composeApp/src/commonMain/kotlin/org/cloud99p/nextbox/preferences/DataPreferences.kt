package org.cloud99p.nextbox.preferences

import org.cloud99p.nextbox.data.local.DataStoreHolder

object DataPreferences : DataStoreHolder() {
    var counter by int("counter", 0)

    var defaultAccount by string("default_account", "")

    var decimal by int("decimal", 0)
}
