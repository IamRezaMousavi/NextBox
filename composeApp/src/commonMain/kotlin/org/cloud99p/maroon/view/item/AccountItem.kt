package org.cloud99p.maroon.view.item

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.cloud99p.maroon.AppViewModel
import org.cloud99p.maroon.data.model.Account
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AccountItem(
    account: Account,
    modifier: Modifier = Modifier,
    onClicked: (Account) -> Unit = {}
) = Card(
    modifier = modifier
        .wrapContentSize()
        .padding(vertical = 16.dp, horizontal = 8.dp)
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.error,
            shape = MaterialTheme.shapes.medium
        )
        .clickable {
            onClicked(account)
        },
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = MaterialTheme.shapes.medium
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
        modifier = modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 24.dp),
        horizontalAlignment = Alignment.Start,
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

@Composable
fun AccountItemPlaceHolder(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit = {}
) = AccountItem(
    modifier = modifier,
    account = Account(name = "Add New Account"),
    onClicked = {
        onClicked()
    }
)
