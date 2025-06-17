package org.cloud99p.maroon.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Transactions")
data class Transaction (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val amount: Double
)