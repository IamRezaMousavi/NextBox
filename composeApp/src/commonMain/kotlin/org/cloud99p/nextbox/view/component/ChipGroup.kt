package org.cloud99p.nextbox.view.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChipGroup(
    items: List<String>,
    defaultSelectedIndex: Int = -1,
    onSelectedChange: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(defaultSelectedIndex) }

    LazyRow(modifier = modifier) {
        itemsIndexed(items = items) { index, item ->
            FilterChip(
                modifier = Modifier.padding(8.dp),
                selected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    onSelectedChange(index)
                },
                label = { Text(item) },
                leadingIcon = if (index == selectedIndex) {
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
