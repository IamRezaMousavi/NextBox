package org.cloud99p.maroon

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.cloud99p.maroon.data.local.AppDatabase
import org.cloud99p.maroon.data.model.Transaction
import org.cloud99p.maroon.view.TransactionItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    db: AppDatabase,
    prefs: DataStore<Preferences>
) {
    val scope = rememberCoroutineScope()
    val counter by prefs
        .data
        .map {
            val counterKey = intPreferencesKey("counter")
            it[counterKey] ?: 0
        }
        .collectAsState(0)

    val transactions by db
        .dao()
        .transactions()
        .collectAsState(emptyList())

    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = counter,
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                }
            ) {
                Text(
                    text = it.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 50.sp
                )
            }
            Button(onClick = {
                scope.launch {
                    prefs.edit { dataStore ->
                        val counterKey = intPreferencesKey("counter")
                        dataStore[counterKey] = counter + 1
                    }
                }
            }) {
                Text("Increment!")
            }

            Button(onClick = {
                scope.launch {
                    db.dao().insert(
                        Transaction(title = "One Transaction", amount = 1500.0)
                    )
                }
            }) {
                Text("Add Transaction!")
            }

            LazyColumn {
                items(transactions) {
                    TransactionItem(
                        transaction = it,
                        onclick = {
                            scope.launch {
                                db.dao().delete(it)
                            }
                        }
                    )
                }
            }
        }
    }
}