package org.cloud99p.nextbox.view.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Badge
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.cloud99p.nextbox.AppViewModel
import org.cloud99p.nextbox.data.model.Account
import org.cloud99p.nextbox.data.model.Transaction
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TransactionItem(
    transaction: Transaction,
    onclick: (transaction: Transaction) -> Unit,
    modifier: Modifier = Modifier
) = Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(12.dp)
        .clickable {
            onclick(transaction)
        }
) {
    val appViewModel = koinViewModel<AppViewModel>()
    val account by appViewModel.account(transaction.account).collectAsState(Account(name = ""))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "home",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.title ?: transaction.category,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Badge(containerColor = MaterialTheme.colorScheme.tertiaryContainer) {
                Text(text = "${account.name} ${account.id}")
            }
        }

        Text(
            text = transaction.amount.toString(),
            color = if (transaction.amount > 0) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.error
            }
        )
    }
}
