package org.cloud99p.nextbox.view.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChipGroup(
    items: List<String>,
    defaultSelectedItem: String = "",
    onSelectedChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf(defaultSelectedItem) }

    LazyRow(modifier = modifier) {
        items(items = items) { item ->
            FilterChip(
                modifier = Modifier.padding(8.dp),
                selected = item == selectedItem,
                onClick = {
                    selectedItem = item
                    onSelectedChanged(item)
                },
                label = { Text(item) },
                leadingIcon = if (item == selectedItem) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "done_icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                }
            )
        }
    }
}
