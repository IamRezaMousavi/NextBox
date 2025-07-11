package org.cloud99p.nextbox.view.item

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.cloud99p.nextbox.AppViewModel
import org.cloud99p.nextbox.data.model.Account
import org.cloud99p.nextbox.preferences.DataPreferences
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AccountItemBox(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) = Card(
    modifier = Modifier
        .defaultMinSize(minWidth = 150.dp)
        .height(130.dp)
        .padding(vertical = 16.dp, horizontal = 8.dp)
        .then(modifier),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    shape = MaterialTheme.shapes.medium,
    content = content
)

@Composable
fun AccountItem(
    account: Account,
    modifier: Modifier = Modifier
) {
    val isSelected by DataPreferences.defaultAccountProperty.stateFlow.collectAsState()

    AccountItemBox(
        modifier = modifier
            .clickable {
                DataPreferences.defaultAccount = account.name
            }
            .let {
                if (account.name == isSelected) {
                    it.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    )
                } else {
                    it
                }
            }
    ) {
        val appViewModel = koinViewModel<AppViewModel>()
        val amount by appViewModel.accountAmount(account).collectAsState(0.0)
        val transactionsCount by appViewModel.accountTransactionsCount(account).collectAsState(0)

        val animatedAmount by animateFloatAsState(
            targetValue = amount.toFloat(),
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            ),
            label = "animated_amount"
        )

        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = account.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = animatedAmount.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = "$transactionsCount Transactions",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = "ID ${account.id}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun AccountItemPlaceHolder(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit = {}
) = AccountItemBox(
    modifier = modifier.clickable { onClicked() }
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "add"
        )
        Text(
            text = "Add New Account",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}
