package org.cloud99p.maroon.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.cloud99p.maroon.AppViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Counter(modifier: Modifier = Modifier) {
    val appViewModel = koinViewModel<AppViewModel>()
    val counter by appViewModel.counter.collectAsState(0)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedCounter(counter)

        Button(onClick = {
            appViewModel.increase()
        }) {
            Text("Increment!")
        }
    }
}
