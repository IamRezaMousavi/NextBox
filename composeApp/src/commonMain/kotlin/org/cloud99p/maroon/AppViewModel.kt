package org.cloud99p.maroon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.cloud99p.maroon.data.local.AppDatabase
import org.cloud99p.maroon.data.model.Account
import org.cloud99p.maroon.data.model.Transaction

class AppViewModel(private val db: AppDatabase) : ViewModel() {
    val transactions = db
        .dao()
        .transactions()

    val amount = db
        .dao()
        .amount()

    val accounts = db
        .dao()
        .accounts()

    fun insert(transaction: Transaction) {
        viewModelScope.launch {
            db.dao().insert(transaction)
        }
    }

    fun insert(account: Account) {
        viewModelScope.launch {
            db.dao().insert(account)
        }
    }

    fun account(id: Int) = db.dao().account(id)
    fun accountAmount(account: Account) = db.dao().accountAmount(account.id)
    fun accountTransactionsCount(account: Account) = db.dao().accountTransactionCount(account.id)

    fun delete(transaction: Transaction) {
        viewModelScope.launch {
            db.dao().delete(transaction)
        }
    }

    fun delete(account: Account) {
        viewModelScope.launch {
            db.dao().delete(account)
        }
    }
}
