package org.cloud99p.maroon.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.cloud99p.maroon.ViewModel
import org.cloud99p.maroon.data.model.Transaction
import org.cloud99p.maroon.util.round
import org.cloud99p.maroon.view.item.TransactionItem

@Composable
fun TransactionList(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    val db = ViewModel.db
    val transactions by db
        .dao()
        .transactions()
        .map { it.reversed() }
        .collectAsState(emptyList())

    val totalAmount = transactions.sumOf { it.amount }

    val lazyListState = rememberLazyListState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedTotal(value = totalAmount.toFloat())

        LazyColumn(
            state = lazyListState,
            modifier = Modifier.weight(1f)
        ) {
            items(
                items = transactions,
                key = { it.id }
            ) {
                TransactionItem(
                    transaction = it,
                    onclick = {
                        scope.launch {
                            db.dao().delete(it)
                        }
                    },
                    modifier = Modifier.animateItem()
                )
            }
        }

        Button(onClick = {
            scope.launch {
                db.dao().insert(
                    Transaction(
                        title = "Transaction ${(Random.nextFloat() * 100).roundToInt()}",
                        amount = Random.nextDouble(from = -1000.0, until = 1000.0).round(3)
                    )
                )
                lazyListState.smoothScrollToTop()
            }
        }) {
            Text("Add Transaction!")
        }
    }
}

suspend fun LazyListState.smoothScrollToTop() {
    if (firstVisibleItemIndex > layoutInfo.visibleItemsInfo.size) {
        scrollToItem(layoutInfo.visibleItemsInfo.size)
    }
    animateScrollToItem(0)
}
