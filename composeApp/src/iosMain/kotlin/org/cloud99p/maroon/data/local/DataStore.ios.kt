package org.cloud99p.maroon.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.cloud99p.maroon.utils.documentDirectory

fun createDataStore(): DataStore<Preferences> = createDataStore {
    documentDirectory() + "/$DATASTORE_FILENAME"
}
