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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.cloud99p.maroon.AppViewModel
import org.cloud99p.maroon.view.item.TransactionItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TransactionList(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val appViewModel = koinViewModel<AppViewModel>()
    val transactions by appViewModel
        .transactions
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
                    onclick = { transaction ->
                        appViewModel.delete(transaction)
                    },
                    modifier = Modifier.animateItem()
                )
            }
        }

        Button(onClick = {
            scope.launch {
                appViewModel.addTransaction()
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
