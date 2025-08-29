package org.cloud99p.nextbox.view.screen.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.cloud99p.nextbox.AppViewModel
import org.cloud99p.nextbox.data.model.Account
import org.cloud99p.nextbox.preferences.DataPreferences
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountAdd(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val appViewModel = koinViewModel<AppViewModel>()

    var name by remember { mutableStateOf("") }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            MediumTopAppBar(
                title = { Text("Add Account") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "arrow_back"
                        )
                    }
                }
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (name.isEmpty()) return@Button
                    val newAccount = Account(name = name)
                    appViewModel.insert(newAccount)
                    if (DataPreferences.defaultAccount.isEmpty()) {
                        DataPreferences.defaultAccount = name
                    }
                    navController.popBackStack()
                }
            ) {
                Text("Save")
            }
        }
    }
}
