package org.cloud99p.maroon.view.screen.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.cloud99p.maroon.AppViewModel
import org.cloud99p.maroon.data.model.Transaction
import org.cloud99p.maroon.view.component.ChipGroup
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionAdd(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val appViewModel = koinViewModel<AppViewModel>()
    val accounts by appViewModel.accounts.collectAsState(emptyList())
    var accountSelected by remember { mutableStateOf(accounts.firstOrNull()) }

    var title by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }

    var amountError by remember { mutableStateOf(false) }

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
                title = {
                    Text("Edit Transaction")
                },
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
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amountText,
                onValueChange = {
                    amountText = it
                    amountError = false
                },
                label = { Text("Amount") },
                isError = amountError,
                supportingText = {
                    if (amountError) {
                        Text("Fill the amount!")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            ChipGroup(
                items = accounts.map { it.name },
                defaultSelectedIndex = 0,
                onSelectedChange = { index -> accountSelected = accounts[index] }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    amountText
                        .toDoubleOrNull()
                        .ifNull {
                            amountError = true
                        }
                        .ifNotNull { amount ->
                            val transaction = Transaction(
                                title = title.ifEmpty { null },
                                amount = amount,
                                account = accountSelected?.id ?: 1
                            )
                            appViewModel.insert(transaction)
                            navController.popBackStack()
                        }
                }
            ) {
                Text("Save")
            }
        }
    }
}

fun <T : Any> T?.ifNotNull(block: (T) -> Unit): T? {
    if (this != null) block(this)
    return this
}

fun <T : Any> T?.ifNull(block: () -> Unit): T? {
    if (this == null) block()
    return this
}
