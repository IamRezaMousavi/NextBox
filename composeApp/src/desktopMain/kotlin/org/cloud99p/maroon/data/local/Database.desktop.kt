package org.cloud99p.maroon.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseFactory {
    actual fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = File(DATABASE_FILENAME)
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile.absolutePath
        )
    }
}
