package org.cloud99p.maroon.view.component

import androidx.compose.foundation.layout.size
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

@Composable
fun Chip(
    label: String,
    selected: Boolean,
    onClicked: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedState by remember { mutableStateOf(selected) }

    FilterChip(
        modifier = modifier,
        selected = selectedState,
        onClick = {
            selectedState = !selectedState
            onClicked(selectedState)
        },
        label = { Text(label) },
        leadingIcon = if (selectedState) {
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
