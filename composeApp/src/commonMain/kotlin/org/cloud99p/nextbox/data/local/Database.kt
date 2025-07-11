package org.cloud99p.nextbox.data.local

import androidx.room.ConstructedBy
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.Update
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import org.cloud99p.nextbox.data.model.Account
import org.cloud99p.nextbox.data.model.Category
import org.cloud99p.nextbox.data.model.Transaction

@Dao
interface DatabaseDao {
    @Insert
    suspend fun insert(item: Transaction)

    @Insert
    suspend fun insert(item: Category)

    @Insert
    suspend fun insert(item: Account)

    @Update
    suspend fun update(item: Transaction)

    @Update
    suspend fun update(item: Category)

    @Update
    suspend fun update(item: Account)

    @Delete
    suspend fun delete(item: Transaction)

    @Delete
    suspend fun delete(item: Account)

    @Delete
    suspend fun delete(item: Category)

    @Query("SELECT count(*) FROM Transactions")
    suspend fun count(): Int

    @Query("SELECT * FROM Transactions")
    fun transactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM Transactions WHERE id = :id LIMIT 1")
    fun transaction(id: String): Flow<Transaction>

    @Query("SELECT sum(amount) FROM Transactions")
    fun amount(): Flow<Double>

    @Query("SELECT * FROM Categories")
    fun categories(): Flow<List<Category>>

    @Query("SELECT * FROM Categories WHERE id = :id LIMIT 1")
    fun category(id: Int): Flow<Category>

    @Query("SELECT sum(amount) FROM Transactions WHERE category = :categoryId")
    fun categoryAmount(categoryId: Int): Flow<Double>

    @Query("SELECT * FROM Accounts")
    fun accounts(): Flow<List<Account>>

    @Query("SELECT * FROM Accounts WHERE id = :id LIMIT 1")
    fun account(id: Int): Flow<Account>

    @Query("SELECT sum(amount) FROM Transactions WHERE account = :accountId")
    fun accountAmount(accountId: Int): Flow<Double>

    @Query("SELECT count(*) FROM Transactions WHERE account = :accountId")
    fun accountTransactionCount(accountId: Int): Flow<Int>
}

@Database(
    entities = [
        Transaction::class,
        Category::class,
        Account::class
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): DatabaseDao
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

fun createDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase = builder
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()
