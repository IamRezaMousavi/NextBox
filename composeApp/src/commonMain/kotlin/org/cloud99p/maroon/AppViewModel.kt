package org.cloud99p.maroon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.cloud99p.maroon.data.local.AppDatabase
import org.cloud99p.maroon.data.model.Transaction
import org.cloud99p.maroon.util.round

class AppViewModel(private val db: AppDatabase) : ViewModel() {

    private val _timer = MutableStateFlow(0)
    val timer = _timer.asStateFlow()

    val transactions = db
        .dao()
        .transactions()

    fun addTransaction() {
        viewModelScope.launch {
            db.dao().insert(
                Transaction(
                    title = "Transaction ${(Random.nextFloat() * 100).roundToInt()}",
                    amount = Random.nextDouble(from = -1000.0, until = 1000.0).round(3)
                )
            )
        }
    }

    fun delete(transaction: Transaction) {
        viewModelScope.launch {
            db.dao().delete(transaction)
        }
    }

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _timer.value++
            }
        }
    }
}
