package org.cloud99p.nextbox.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
