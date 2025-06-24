package org.cloud99p.maroon.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.cloud99p.maroon.ViewModel

@Composable
fun Counter(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    val prefs = ViewModel.pref
    val counter by prefs
        .data
        .map {
            val counterKey = intPreferencesKey("counter")
            it[counterKey] ?: 0
        }
        .collectAsState(0)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedCounter(counter)

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
    }
}
