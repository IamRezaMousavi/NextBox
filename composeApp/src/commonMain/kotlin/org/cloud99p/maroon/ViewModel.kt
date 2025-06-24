package org.cloud99p.maroon

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import org.cloud99p.maroon.data.local.AppDatabase
import org.cloud99p.maroon.data.local.createDatabase

object ViewModel {
    lateinit var db: AppDatabase
    lateinit var pref: DataStore<Preferences>

    fun initial(
        dbBuilder: RoomDatabase.Builder<AppDatabase>,
        preferences: DataStore<Preferences>
    ) {
        db = createDatabase(dbBuilder)
        pref = preferences
    }
}
