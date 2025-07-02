package org.cloud99p.maroon.view.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.map
import org.cloud99p.maroon.AppViewModel
import org.cloud99p.maroon.view.Screen
import org.cloud99p.maroon.view.item.AccountItem
import org.cloud99p.maroon.view.item.AccountItemPlaceHolder
import org.cloud99p.maroon.view.item.TransactionItem
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) = Scaffold(
    modifier = modifier
        .fillMaxSize()
        .safeContentPadding(),
    topBar = {
        TopAppBar(
            title = { Text("Transactions") }
        )
    },
    floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate(Screen.TransactionAdd) }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add"
            )
        }
    }
) { paddingValues ->

    val appViewModel = koinViewModel<AppViewModel>()

    val accounts by appViewModel
        .accounts
        .collectAsState(emptyList())

    val transactions by appViewModel
        .transactions
        .map { it.reversed() }
        .collectAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            items(items = accounts, key = { it.id }) { account ->
                AccountItem(
                    account = account,
                    onClicked = {
                        appViewModel.delete(account)
                    }
                )
            }

            item {
                AccountItemPlaceHolder(
                    onClicked = {
                        navController.navigate(Screen.AccountAdd)
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
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
    }
}
