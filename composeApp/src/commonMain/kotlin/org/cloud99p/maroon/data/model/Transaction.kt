package org.cloud99p.maroon.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "Transactions")
data class Transaction
@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
constructor(
    @PrimaryKey
    val id: String = Uuid.random().toString(),
    val title: String? = null,
    val category: String = "Category",
    val account: Int,
    val amount: Double,
    val dateCreated: Long = Clock.System.now().toEpochMilliseconds(),
    val dateModified: Long? = null
)
