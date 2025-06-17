package org.cloud99p.maroon.data.local

import androidx.room.ConstructedBy
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import org.cloud99p.maroon.data.model.Transaction

@Dao
interface DBDao {
    @Insert
    suspend fun insert(item: Transaction)

    @Delete
    suspend fun delete(item: Transaction)

    @Query("SELECT count(*) FROM Transactions")
    suspend fun count(): Int

    @Query("SELECT * FROM Transactions")
    fun transactions(): Flow<List<Transaction>>
}

@Database(
    entities = [Transaction::class],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): DBDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

internal const val DATABASE_FILENAME = "app.db"

expect class DatabaseFactory {
    fun createDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
}

fun createDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
