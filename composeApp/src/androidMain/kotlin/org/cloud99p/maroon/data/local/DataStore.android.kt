package org.cloud99p.maroon.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStore(context: Context): DataStore<Preferences> = createDataStore {
    context.filesDir.resolve(DATASTORE_FILENAME).absolutePath
}
