package org.cloud99p.maroon.preferences

import org.cloud99p.maroon.data.local.DataStoreHolder

object DataPreferences : DataStoreHolder() {
    val counterProperty = int("counter", 0)
    var counter by counterProperty
}
