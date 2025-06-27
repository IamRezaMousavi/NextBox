package org.cloud99p.maroon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.cloud99p.maroon.view.Navigation
import org.cloud99p.maroon.view.ui.MaroonTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(modifier: Modifier = Modifier) = MaroonTheme {
    Navigation()
}
