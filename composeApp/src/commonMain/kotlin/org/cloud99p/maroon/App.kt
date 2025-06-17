package org.cloud99p.maroon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
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

    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = counter.toString(),
                textAlign = TextAlign.Center,
                fontSize = 50.sp
            )
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
}