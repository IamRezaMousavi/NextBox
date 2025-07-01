package org.cloud99p.maroon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.cloud99p.maroon.view.Navigation
import org.cloud99p.maroon.view.ui.MaroonTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(modifier: Modifier = Modifier) = MaroonTheme {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Navigation()
    }
}
