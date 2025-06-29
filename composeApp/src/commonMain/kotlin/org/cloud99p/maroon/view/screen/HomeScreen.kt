package org.cloud99p.maroon.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.cloud99p.maroon.AppViewModel
import org.cloud99p.maroon.preferences.DataPreferences
import org.cloud99p.maroon.view.Screen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val appViewModel = koinViewModel<AppViewModel>()
    val timer by appViewModel.timer.collectAsState()

    Surface(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(timer.toString())

            Button(onClick = {
                navController.navigate(Screen.Counter)
            }) {
                Text("Counter Screen")
            }

            Button(onClick = {
                navController.navigate(Screen.Transactions)
            }) {
                Text("Transactions Screen")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                DataPreferences.changeTheme()
            }) {
                Text(DataPreferences.theme.name)
            }
        }
    }
}
