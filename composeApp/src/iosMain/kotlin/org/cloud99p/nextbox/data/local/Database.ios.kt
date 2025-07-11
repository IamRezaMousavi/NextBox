package org.cloud99p.nextbox.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import org.cloud99p.nextbox.utils.documentDirectory

actual class DatabaseFactory {
    actual fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFilePath = documentDirectory() + "/$DATABASE_FILENAME"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFilePath
        )
    }
}
